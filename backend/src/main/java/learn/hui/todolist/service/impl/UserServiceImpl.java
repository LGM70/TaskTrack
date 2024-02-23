package learn.hui.todolist.service.impl;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import learn.hui.todolist.pojo.TaskList;
import learn.hui.todolist.pojo.User;
import learn.hui.todolist.repository.UserRepository;
import learn.hui.todolist.service.ListService;
import learn.hui.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ListService listService;

    @Autowired
    private MongoTemplate mongoTemplate;

//    @Override
//    public List<User> allUsers() {
//        return userRepository.findAll();
//    }
//
//    @Override
//    public Optional<User> singleUser(String name) {
//        return userRepository.findByName(name);
//    }

    @Override
    public User createUser(String name, String password) {
        return userRepository.insert(new User(name, password));
    }

    @Override
    public Optional<User> verifyPassword(String name, String password) {
        List<User> userList = mongoTemplate.find(new Query(
                Criteria.where("name").is(name)
                        .and("password").is(password)),
                User.class);
        if (userList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(userList.get(0));
    }

    @Override
    public boolean resetPassword(String name, String password, String newPassword) {
        UpdateResult result = mongoTemplate.update(User.class)
                .matching(new Criteria("name").is(name).and("password").is(password))
                .apply(new Update().set("password", newPassword))
                .first();
        return result.getModifiedCount() == 1L;
    }

    @Override
    public boolean deleteUser(String name, String password) {
        Optional<User> user = verifyPassword(name, password);
        if (user.isEmpty()) {
            return false;
        }
        for (TaskList list : user.get().getOwnedListIds()) {
            listService.deleteList(user.get().getName(), list.getId());
        }
        DeleteResult result = mongoTemplate.remove(User.class)
                .matching(new Criteria("name").is(name).and("password").is(password))
                .one();
        return result.getDeletedCount() == 1L;
    }
}
