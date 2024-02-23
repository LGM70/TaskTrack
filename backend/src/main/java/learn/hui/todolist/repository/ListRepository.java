package learn.hui.todolist.repository;

import learn.hui.todolist.pojo.TaskList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListRepository extends MongoRepository<TaskList, String> {
//    Optional<TaskList> findByTitle(String title);
}
