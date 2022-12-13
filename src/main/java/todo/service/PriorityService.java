package todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import todo.model.Priority;
import todo.store.PriorityStore;

import java.util.List;

@Service
@AllArgsConstructor
public class PriorityService {
    private final PriorityStore priorityStore;

    public List<Priority> findAll() {
        return priorityStore.findAll();
    }
}
