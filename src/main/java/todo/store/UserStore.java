package todo.store;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import todo.model.User;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserStore {
    private final Logger logger = LoggerFactory.getLogger(UserStore.class);
    private final CrudRepository crudRepository;

    public Optional<User> findUserByUsernameAndPassword(String username, String password) {
        return crudRepository.optional("from User where username = :fUsername and password = :fPassword",
                User.class, Map.of("fUsername", username, "fPassword", password));
    }

    public Optional<User> add(User user) {
        try {
            crudRepository.run(session -> session.persist(user));
            return Optional.of(user);
        } catch (Exception e) {
            logger.error(e.toString(), e);
            return Optional.empty();
        }
    }

    public Optional<User> findById(int id) {
        return crudRepository.optional("from User where id = :fId", User.class, Map.of("fId", id));
    }

    public boolean update(int id, User user) {
        return crudRepository.executeUpdate("update User set user_zone = :fZone where id = :fId",
                Map.of("fZone", user.getTimeZone(), "fId", id)) != 0;
    }
}
