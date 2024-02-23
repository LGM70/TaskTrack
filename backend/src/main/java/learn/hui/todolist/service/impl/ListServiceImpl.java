package learn.hui.todolist.service.impl;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import learn.hui.todolist.pojo.Task;
import learn.hui.todolist.pojo.TaskList;
import learn.hui.todolist.pojo.User;
import learn.hui.todolist.repository.ListRepository;
import learn.hui.todolist.repository.TaskRepository;
import learn.hui.todolist.repository.UserRepository;
import learn.hui.todolist.service.ListService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ListServiceImpl implements ListService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ListRepository listRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<List<TaskList>> allLists(String username) {
        List<List<TaskList>> result = new ArrayList<>();
        result.add(allOwnedLists(username));
        if (result.get(0) == null) {
            return null;
        }
        result.add(allSharedLists(username));
        return result;
    }

    @Override
    public List<TaskList> allOwnedLists(String username) {
        Optional<User> user = userRepository.findByName(username);
        return user.map(User::getOwnedListIds).orElse(null);
    }

    @Override
    public List<TaskList> allSharedLists(String username) {
        Optional<User> user = userRepository.findByName(username);
        return user.map(User::getSharedListIds).orElse(null);
    }

    @Override
    public Optional<TaskList> isAuthorizedList(String username, String listId) {
        Optional<TaskList> list1 = isOwnedList(username, listId);
        Optional<TaskList> list2 = isSharedList(username, listId);
        return list1.isPresent() ? list1 : list2;
    }

    @Override
    public Optional<TaskList> isOwnedList(String username, String listId) {
        List<User> queryResult = mongoTemplate.find(new Query(
                Criteria.where("name").is(username)
                        .and("ownedListIds").elemMatch(
                                Criteria.where("$eq").is(new ObjectId(listId))
                        )
                ), User.class);
        if (queryResult.isEmpty()) {
            return Optional.empty();
        }
        return listRepository.findById(listId);
    }

    @Override
    public Optional<TaskList> isSharedList(String username, String listId) {
        List<User> queryResult = mongoTemplate.find(new Query(
                Criteria.where("name").is(username)
                        .and("sharedListIds").elemMatch(
                                Criteria.where("$eq").is(new ObjectId(listId))
                        )
        ), User.class);
        if (queryResult.isEmpty()) {
            return Optional.empty();
        }
        return listRepository.findById(listId);
    }

    @Override
    public Optional<TaskList> createList(String username, String title, String description) {
        if (userRepository.findByName(username).isEmpty()) {
            return Optional.empty();
        }
        TaskList list = new TaskList(title, description);
        list = mongoTemplate.insert(list);
        mongoTemplate.update(User.class)
                .matching(Criteria.where("name").is(username))
                .apply(new Update().push("ownedListIds").value(list)).first();
        return Optional.of(list);
    }

    @Override
    public boolean deleteList(String username, String listId) {
        Optional<TaskList> list = isOwnedList(username, listId);
        if (list.isEmpty()) {
            return false;
        }
        for (Task task : list.get().getTaskIds()) {
//            taskService.deleteTask(username, listId, task.getId());
            taskRepository.delete(task);
        }
        List<User> shared = mongoTemplate.find(new Query(Criteria.where("sharedListIds")
                        .elemMatch(Criteria.where("$eq").is(new ObjectId(listId)))
                ), User.class);
        for (User sharing : shared) {
            revokeShareList(username, listId, sharing.getName());
        }
        UpdateResult result1 = mongoTemplate.update(User.class)
                .matching(Criteria.where("name").is(username))
                .apply(new Update().pull("ownedListIds", list.get())).first();
        if (result1.getModifiedCount() == 1L) {
            DeleteResult result2 = mongoTemplate.remove(new Query(Criteria.where("id").is(listId)), TaskList.class);
            return result2.getDeletedCount() == 1L;
        }
        return false;
    }

    @Override
    public boolean updateTitle(String username, String listId, String title) {
        Optional<TaskList> list = isAuthorizedList(username, listId);
        if (list.isEmpty()) {
            return false;
        }
        list.get().setTitle(title);
        mongoTemplate.save(list.get());
        return true;
    }

    @Override
    public boolean updateDescription(String username, String listId, String description) {
        Optional<TaskList> list = isAuthorizedList(username, listId);
        if (list.isEmpty()) {
            return false;
        }
        list.get().setDescription(description);
        mongoTemplate.save(list.get());
        return true;
    }

    @Override
    public boolean shareList(String username, String listId, String sharedUsername) {
        Optional<TaskList> list = isOwnedList(username, listId);
        if (list.isEmpty()) {
            return false;
        }
        if (username.equals(sharedUsername)) {
            return false;
        }
        Optional<TaskList> tmp = isSharedList(sharedUsername, listId);
        if (tmp.isPresent()) {
            return true;
        }
        UpdateResult result = mongoTemplate.update(User.class)
                .matching(Criteria.where("name").is(sharedUsername))
                .apply(new Update().push("sharedListIds", list.get()))
                .first();
        return result.getModifiedCount() == 1L;
    }

    @Override
    public boolean revokeShareList(String username, String listId, String sharedUsername) {
        Optional<TaskList> list = isOwnedList(username, listId);
        if (list.isEmpty()) {
            return false;
        }
        if (username.equals(sharedUsername)) {
            return false;
        }
        UpdateResult result = mongoTemplate.update(User.class)
                .matching(Criteria.where("name").is(sharedUsername))
                .apply(new Update().pull("sharedListIds", list.get()))
                .first();
//        return result.getModifiedCount() == 1L;
        return true;
    }
}
