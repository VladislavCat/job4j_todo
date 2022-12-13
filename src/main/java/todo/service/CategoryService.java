package todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import todo.model.Category;
import todo.store.CategoryStore;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryStore categoryStore;

    public List<Category> findAll() {
        return categoryStore.findAll();
    }

    public Optional<Category> findById(int i) {
        return categoryStore.findById(i);
    }
}
