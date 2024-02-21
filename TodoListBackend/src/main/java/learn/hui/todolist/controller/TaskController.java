package learn.hui.todolist.controller;

import learn.hui.todolist.pojo.Task;
import learn.hui.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping("/{listId}")
    public ResponseEntity<Optional<Task>> createTask(@RequestParam String username, @PathVariable String listId, @RequestBody Map<String, String> payload) {
        Optional<Task> item = taskService.createTask(username, listId, payload.get("taskBody"));
        if (item.isEmpty()) {
            return new ResponseEntity<>(Optional.empty(), HttpStatus.UNAUTHORIZED);
        }
        else {
            return new ResponseEntity<>(item, HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{listId}")
    public ResponseEntity<Boolean> deleteTask(@RequestParam String username, @PathVariable String listId, @RequestParam String taskId) {
        boolean result = taskService.deleteTask(username, listId, taskId);
        if (!result) {
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
        else {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
    }

    @PutMapping("/{listId}")
    public ResponseEntity<Boolean> setStatus(@RequestParam String username, @PathVariable String listId, @RequestParam String taskId, @RequestParam boolean status) {
        boolean result = taskService.setTaskStatus(username, listId, taskId, status);
        if (!result) {
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
        else {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
    }
}
