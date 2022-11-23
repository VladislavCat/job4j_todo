package todo.store;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import todo.Main;
import todo.model.User;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class UserStore {
    private final SessionFactory sf;
    private final Logger logger = LoggerFactory.getLogger(UserStore.class);

    public List<User> findAllUsers() {
        List<User> rsl = new ArrayList<>();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from User");
            for (Object o : query.list()) {
                rsl.add((User) o);
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return rsl;
    }
}
