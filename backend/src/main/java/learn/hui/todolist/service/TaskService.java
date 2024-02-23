package learn.hui.todolist.service;

import learn.hui.todolist.pojo.Task;

import java.util.Optional;

public interface TaskService {
    /***
     * Get a task
     * @param username by which user
     * @param listId in which list
     * @param taskId task id
     * @return the task
     */
    Optional<Task> singleTask(String username, String listId, String taskId);

    /***
     * Create a new task
     * @param username by which user
     * @param listId in which list
     * @param taskBody the body of the task
     * @return the created task
     */
    Optional<Task> createTask(String username, String listId, String taskBody);

    /***
     * Finish or unfinish the task
     * @param username by which user
     * @param listId in which list
     * @param taskId task id
     * @param status new status
     * @return the result
     */
    boolean setTaskStatus(String username, String listId, String taskId, boolean status);

    /***
     * Delete a task
     * @param username by which user
     * @param listId in which list
     * @param taskId task id
     * @return the result
     */
    boolean deleteTask(String username, String listId, String taskId);
}
