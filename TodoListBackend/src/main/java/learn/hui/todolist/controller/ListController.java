package learn.hui.todolist.controller;

import learn.hui.todolist.pojo.TaskList;
import learn.hui.todolist.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/lists")
public class ListController {
    @Autowired
    private ListService listService;

    @GetMapping
    public ResponseEntity<List<List<TaskList>>> getAllLists(@RequestParam String username) {
        List<List<TaskList>> result = listService.allLists(username);
        if (result == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @GetMapping("/{listId}")
    public ResponseEntity<Optional<TaskList>> getSingleList(@PathVariable String listId, @RequestParam String username) {
        Optional<TaskList> list = listService.isAuthorizedList(username, listId);
        if (list.isEmpty()) {
            return new ResponseEntity<>(Optional.empty(), HttpStatus.UNAUTHORIZED);
        }
        else {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<Optional<TaskList>> createList(@RequestParam String username, @RequestBody Map<String, String> payload) {
        Optional<TaskList> list = listService.createList(username,
                payload.get("title"),
                payload.get("description"));
        if (list.isEmpty()) {
            return new ResponseEntity<>(Optional.empty(), HttpStatus.UNAUTHORIZED);
        }
        else {
            return new ResponseEntity<>(list, HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{listId}")
    public ResponseEntity<Boolean> deleteList(@RequestParam String username, @PathVariable String listId) {
        boolean result = listService.deleteList(username, listId);
        if (!result) {
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
        else {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
    }

    @PutMapping("/{listId}/description")
    public ResponseEntity<Optional<TaskList>> updateDescription(@RequestParam String username, @PathVariable String listId, @RequestBody Map<String, String> payload) {
        boolean result = listService.updateDescription(username, listId, payload.get("description"));
        if (!result) {
            return new ResponseEntity<>(Optional.empty(), HttpStatus.UNAUTHORIZED);
        }
        else {
            return new ResponseEntity<>(listService.isAuthorizedList(username, listId), HttpStatus.OK);
        }
    }

    @PutMapping("/{listId}/title")
    public ResponseEntity<Optional<TaskList>> updateTitle(@RequestParam String username, @PathVariable String listId, @RequestBody Map<String, String> payload) {
        boolean result = listService.updateTitle(username, listId, payload.get("title"));
        if (!result) {
            return new ResponseEntity<>(Optional.empty(), HttpStatus.UNAUTHORIZED);
        }
        else {
            return new ResponseEntity<>(listService.isAuthorizedList(username, listId), HttpStatus.OK);
        }
    }

    @PostMapping("/{listId}/share")
    public ResponseEntity<Boolean> shareList(@PathVariable String listId, @RequestParam String username, @RequestParam String sharedUsername) {
        boolean result = listService.shareList(username, listId, sharedUsername);
        if (!result) {
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
        else {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{listId}/share")
    public ResponseEntity<Boolean> revokeShareList(@PathVariable String listId, @RequestParam String username, @RequestParam String sharedUsername) {
        boolean result = listService.revokeShareList(username, listId, sharedUsername);
        if (!result) {
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
        else {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
    }
}
