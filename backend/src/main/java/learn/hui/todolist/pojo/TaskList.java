package learn.hui.todolist.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document(collection = "lists")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskList {
    @Id
    private String id;
    private String title;
    private String description;
    @DocumentReference
    private List<Task> taskIds;

    public TaskList(String title) {
        this.title = title;
    }

    public TaskList(String title, String description) {
        this.title = title;
        this.description = description == null ? "" : description;
    }
}
