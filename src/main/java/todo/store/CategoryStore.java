package todo.store;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import todo.model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CategoryStore {
    private final CrudRepository crudRepository;
    private final Logger logger = LoggerFactory.getLogger(TaskStore.class);

    public List<Category> findAll() {
        return crudRepository.query("from Category", Category.class);
    }

    public Optional<Category> findById(int i) {
        return crudRepository.optional("from Category where id = :fId", Category.class, Map.of("fId", i));
    }

}
