package todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import todo.model.Task;
import todo.store.TaskStore;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TasksService {
    private final TaskStore taskStore;

    public void add(Task task) {
        taskStore.add(task);
    }

    public List<Task> findAll() {
        return taskStore.findAll();
    }

    public List<Task> findAllCompletedTasks() {
        return taskStore.findAllDoneTask(true);
    }

    public List<Task> findAllUnexecutedTask() {
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
