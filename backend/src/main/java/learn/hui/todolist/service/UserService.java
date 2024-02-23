package learn.hui.todolist.service;

import learn.hui.todolist.pojo.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
//    /***
//     * Get all users
//     * @return list of all users
//     */
//    List<User> allUsers();
//
//    /***
//     * Get a user by name
//     * @param name username
//     * @return the user
//     */
//    Optional<User> singleUser(String name);

    /***
     * Create a new user
     * @param name unique username
     * @param password password
     * @return the created user
     */
    User createUser(String name, String password);

    /***
     * Verify password for a user
     * @param name username
     * @param password password
     * @return the user
     */
    Optional<User> verifyPassword(String name, String password);

    /***
     * Reset the password of a user
     * @param name username
     * @param password old password
     * @param newPassword new password
     * @return the result of update
     */
    boolean resetPassword(String name, String password, String newPassword);

    /***
     * Delete a existing user
     * @param name username
     * @param password password
     * @return the result of deletion
     */
    boolean deleteUser(String name, String password);
}
