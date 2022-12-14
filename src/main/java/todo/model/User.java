package todo.model;

import lombok.*;

import javax.persistence.*;
import java.util.TimeZone;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    @Column(name = "user_zone")
    private String timeZone;
}
