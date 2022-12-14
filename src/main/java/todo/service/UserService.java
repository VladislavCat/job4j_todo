package todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import todo.model.User;
import todo.store.UserStore;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserStore userStore;

    public Optional<User> add(User user) {
        return userStore.add(user);
    }

    public Optional<User> findUserByUsernameAndPassword(String username, String password) {
        return userStore.findUserByUsernameAndPassword(username, password);
    }

    public Optional<User> findById(int id) {
        return userStore.findById(id);
    }

    public boolean update(int id, User user) {
        return userStore.update(id, user);
    }
}
