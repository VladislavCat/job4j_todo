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

    public List<Task> findAll() {
        return taskStore.findAll();
    }

    public List<Task> findAllCompletedTasks() {
        return taskStore.findAllCompletedTasks();
    }

    public List<Task> findAllUnexecutedTask() {
        return taskStore.findAllUnexecutedTask();
    }

    public Task findById(int id) {
        return taskStore.findById(id);
    }

    public void executeTask(int id) {
        taskStore.executeTask(id);
    }

    public void deleteTask(int id) {
        taskStore.deleteTask(id);
    }

    public void updateTask(int id, Task task) {
        taskStore.updateTask(id, task);
    }

}
