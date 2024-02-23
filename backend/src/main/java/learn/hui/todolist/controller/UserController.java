package learn.hui.todolist.controller;

import learn.hui.todolist.pojo.User;
import learn.hui.todolist.service.UserService;
import learn.hui.todolist.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping
    public ResponseEntity<Boolean> register(@RequestParam String username, @RequestParam String password) {
        userService.createUser(username, password);
        return new ResponseEntity<>(true, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Optional<String>> login(@RequestParam String username, @RequestParam String password) {
        Optional<User> user = userService.verifyPassword(username, password);
        if (user.isEmpty()) {
            return new ResponseEntity<>(Optional.empty(), HttpStatus.UNAUTHORIZED);
        }
        else {
            String token = jwtUtils.generateJwt(username);
            return new ResponseEntity<>(Optional.of(token), HttpStatus.OK);
        }
    }


    @PutMapping
    public ResponseEntity<Boolean> resetPassword(@RequestParam String username, @RequestParam String password, @RequestParam String newPassword) {
        boolean flag = userService.resetPassword(username, password, newPassword);
        if (flag) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteAccount(@RequestParam String username, @RequestParam String password) {
        boolean flag = userService.deleteUser(username, password);
        if (flag) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
    }
}
