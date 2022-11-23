package todo.store;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import todo.model.Task;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class TaskStore {
    private final SessionFactory sf;
    private final Logger logger = LoggerFactory.getLogger(TaskStore.class);

    public List<Task> findAll() {
        List<Task> rsl = new ArrayList<>();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from Task");
            for (Object o : query.list()) {
                rsl.add((Task) o);
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return rsl;
    }

    public List<Task> findAllCompletedTasks() {
        List<Task> rsl = new ArrayList<>();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from Task as t where t.done = true");
            for (Object o : query.list()) {
                rsl.add((Task) o);
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return rsl;
    }

    public List<Task> findAllUnexecutedTask() {
        List<Task> rsl = new ArrayList<>();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from Task as t where t.done = false");
            for (Object o : query.list()) {
                rsl.add((Task) o);
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return rsl;
    }
}
