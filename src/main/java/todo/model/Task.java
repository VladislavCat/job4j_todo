package todo.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NonNull
    private String description;
    @ManyToOne
    @JoinColumn(name = "idUser")
    @NonNull
    private User user;
    @NonNull
    private LocalDateTime created;
    @NonNull
    private boolean done;

}
