package learn.hui.todolist.service.impl;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import learn.hui.todolist.pojo.Task;
import learn.hui.todolist.repository.TaskRepository;
import learn.hui.todolist.pojo.TaskList;
import learn.hui.todolist.service.ListService;
import learn.hui.todolist.service.TaskService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ListService listService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Optional<Task> singleTask(String username, String listId, String taskId) {
        Optional<TaskList> list = listService.isAuthorizedList(username, listId);
        if (list.isEmpty()) {
            return Optional.empty();
        }
        List<TaskList> queryResult = mongoTemplate.find(new Query(
                Criteria.where("id").is(listId)
                        .and("taskIds").elemMatch(
                                Criteria.where("$eq").is(new ObjectId(taskId))
                        )
                ), TaskList.class);
        if (queryResult.isEmpty()) {
            return Optional.empty();
        }
        return taskRepository.findById(taskId);
    }

    @Override
    public Optional<Task> createTask(String username, String listId, String taskBody) {
        Optional<TaskList> list = listService.isAuthorizedList(username, listId);
        if (list.isEmpty()) {
            return Optional.empty();
        }
        Task task = taskRepository.insert(new Task(taskBody));
        mongoTemplate.update(TaskList.class)
                .matching(Criteria.where("id").is(listId))
                .apply(new Update().push("taskIds", task))
                .first();
        return Optional.of(task);
    }

    @Override
    public boolean setTaskStatus(String username, String listId, String taskId, boolean status) {
        Optional<Task> task = singleTask(username, listId, taskId);
        if (task.isEmpty()) {
            return false;
        }
        task.get().setDone(status);
        taskRepository.save(task.get());
        return true;
    }

    @Override
    public boolean deleteTask(String username, String listId, String taskId) {
        Optional<Task> task = singleTask(username, listId, taskId);
        if (task.isEmpty()) {
            return false;
        }
        UpdateResult result1 = mongoTemplate.update(TaskList.class)
                .matching(Criteria.where("id").is(listId))
                .apply(new Update().pull("taskIds", task.get()))
                .first();
        if (result1.getModifiedCount() != 1L) {
            return false;
        }
        DeleteResult result2 = mongoTemplate.remove(task.get());
        return result2.getDeletedCount() == 1L;
    }
}
