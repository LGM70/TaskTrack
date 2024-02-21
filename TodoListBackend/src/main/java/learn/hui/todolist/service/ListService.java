package learn.hui.todolist.service;

import learn.hui.todolist.pojo.TaskList;

import java.util.List;
import java.util.Optional;

public interface ListService {
    /***
     * Get all lists of a user
     * @param username username
     * @return lists of owned and shared lists
     */
    List<List<TaskList>> allLists(String username);

    /***
     * Get all owned lists of a user
     * @param username username
     * @return lists of owned lists
     */
    List<TaskList> allOwnedLists(String username);

    /***
     * Get all shared lists of a user
     * @param username username
     * @return lists of shared lists
     */
    List<TaskList> allSharedLists(String username);

    /***
     * Get a list by id
     * @param username username
     * @param listId list id
     * @return the list if the user is authorized
     */
    Optional<TaskList> isAuthorizedList(String username, String listId);

    /***
     * Get an owned list by id
     * @param username username
     * @param listId list id
     * @return the list if the user is the owner
     */
    Optional<TaskList> isOwnedList(String username, String listId);

    /***
     * Get a shared list by id
     * @param username username
     * @param listId list id
     * @return the list if the user is shared
     */
    Optional<TaskList> isSharedList(String username, String listId);

    /***
     * Create a new task list
     * @param username list owner
     * @param title list title
     * @param description list description, optional
     * @return the created list
     */
    Optional<TaskList> createList(String username, String title, String description);

    /***
     * Delete a existing task list
     * @param username list owner
     * @param listId list id
     * @return the result
     */
    boolean deleteList(String username, String listId);

    /***
     * Update the title of list
     * @param username list owner
     * @param listId list id
     * @param title new list title
     * @return the result
     */
    boolean updateTitle(String username, String listId, String title);

    /***
     * Update the description of list
     * @param username list owner
     * @param listId list id
     * @param description new list description
     * @return the result
     */
    boolean updateDescription(String username, String listId, String description);

    /***
     * share a list with another user
     * @param username list owner
     * @param listId list id
     * @param sharedUsername user to share the list
     * @return the result
     */
    boolean shareList(String username, String listId, String sharedUsername);

    /***
     * revoke the sharing of a list with another user
     * @param username list owner
     * @param listId list id
     * @param sharedUsername user whose sharing is revoked
     * @return the result
     */
    boolean revokeShareList(String username, String listId, String sharedUsername);
}
