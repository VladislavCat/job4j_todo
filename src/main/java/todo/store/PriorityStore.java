package todo.store;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import todo.model.Priority;

import java.util.List;
@Repository
@AllArgsConstructor
public class PriorityStore {
    private final CrudRepository crudRepository;
    private final Logger logger = LoggerFactory.getLogger(TaskStore.class);


    public List<Priority> findAll() {
        return crudRepository.query("from Priority", Priority.class);
    }
}
