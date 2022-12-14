package todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import todo.model.Category;
import todo.model.Task;
import todo.store.TaskStore;

import java.time.ZoneId;
import java.util.*;

@AllArgsConstructor
@Service
public class TasksService {
    private final TaskStore taskStore;
    private final ZoneId defaultZoneId = TimeZone.getDefault().toZoneId();

    public void add(Task task, List<Integer> categoryId, CategoryService categoryService) {
        List<Category> categories = new ArrayList<>();
        for (int i : categoryId) {
            categories.add(categoryService.findById(i).orElseThrow(()
                    -> new NoSuchElementException("Категория не найдена " + i)));
        }
        task.setCategories(categories);
        taskStore.add(task);
    }

    public Set<Task> findAll(ZoneId timeZone) {
        return updateTimeInTask(timeZone, taskStore.findAll());
    }

    public Set<Task> findAllCompletedTasks(ZoneId timeZone) {
        return updateTimeInTask(timeZone, taskStore.findAllDoneTask(true));
    }

    public Set<Task> findAllUnexecutedTask(ZoneId timeZone) {
        return updateTimeInTask(timeZone, taskStore.findAllDoneTask(false));
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

    private Set<Task> updateTimeInTask(ZoneId timeZone, Set<Task> taskSet) {
        taskSet.forEach(task -> task.setCreated(task.getCreated().atZone(TimeZone.getDefault().toZoneId())
                .withZoneSameInstant(timeZone).toLocalDateTime()));
        return taskSet;
    }
}
