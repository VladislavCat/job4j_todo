package todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import todo.model.Category;
import todo.model.Task;
import todo.store.TaskStore;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@AllArgsConstructor
@Service
public class TasksService {
    private final TaskStore taskStore;

    public void add(Task task, List<Integer> categoryId, CategoryService categoryService) {
        List<Category> categories = new ArrayList<>();
        for (int i : categoryId) {
            categories.add(categoryService.findById(i).orElseThrow(()
                    -> new NoSuchElementException("Категория не найдена " + i)));
        }
        task.setCategories(categories);
        taskStore.add(task);
    }

    public Set<Task> findAll() {
        return taskStore.findAll();
    }

    public Set<Task> findAllCompletedTasks() {
        return taskStore.findAllDoneTask(true);
    }

    public Set<Task> findAllUnexecutedTask() {
        return taskStore.findAllDoneTask(false);
    }

    public Optional<Task> findById(int id) {
        return taskStore.findById(id);
    }

    public boolean executeTask(int id) {
        return taskStore.executeTask(id);
    }

    public boolean deleteTask(int id) {
        return taskStore.deleteTask(id);
    }

    public boolean updateTask(int id, Task task) {
        return taskStore.updateTask(id, task);
    }
}
