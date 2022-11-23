package todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import todo.model.Task;
import todo.store.TaskStore;

import java.util.List;

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

}
