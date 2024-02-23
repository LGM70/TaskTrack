package learn.hui.todolist.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    private String password; // encrypted afterwards
    @DocumentReference
    private List<TaskList> ownedListIds;
    @DocumentReference
    private List<TaskList> sharedListIds;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
