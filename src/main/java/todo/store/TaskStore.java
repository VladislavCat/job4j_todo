package todo.store;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import todo.Main;
import todo.model.Task;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class TaskStore {
    private final CrudRepository crudRepository;

    public void add(Task task) {
        crudRepository.run(session -> session.persist(task));
    }

    public List<Task> findAll() {
        return crudRepository.query("select distinct t from Task t left join fetch t.categories join fetch t.priority",
                Task.class);
    }

    public List<Task> findAllDoneTask(boolean done) {
        return crudRepository.query("select distinct t from Task t left join fetch t.priority join fetch t.categories where done = :fDone",
                Task.class, Map.of("fDone", done));
    }

    public Optional<Task> findById(int id) {
        return crudRepository.optional("from Task t left join fetch t.priority where t.id = :fId",
                Task.class, Map.of("fId", id));
    }

    public boolean executeTask(int id) {
        return crudRepository.executeUpdate("update Task set done = true where id = :fId ",
                Map.of("fId", id)) != 0;
    }

    public boolean deleteTask(int id) {
        return crudRepository.executeUpdate("delete Task where id = :fId", Map.of("fId", id)) != 0;
    }

    public boolean updateTask(int id, Task task) {
        return crudRepository.executeUpdate("update Task set description = :fDesc where id = :fId",
                Map.of("fDesc", task.getDescription(), "fId", id)) != 0;
    }
}
