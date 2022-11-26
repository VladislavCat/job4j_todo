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
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TaskStore {
    private final SessionFactory sf;
    private final Logger logger = LoggerFactory.getLogger(TaskStore.class);

    public void add(Task task) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
    }

    public List<Task> findAll() {
        List<Task> rsl = new ArrayList<>();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query<Task> query = session.createQuery("from Task order by id", Task.class);
            rsl.addAll(query.list());
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return rsl;
    }

    public List<Task> findAllDoneTask(boolean done) {
        List<Task> rsl = new ArrayList<>();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query<Task> query = session.createQuery("from Task as t where t.done = :fBool order by id", Task.class).setParameter("fBool", done);
            rsl.addAll(query.list());
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return rsl;
    }

    public Optional<Task> findById(int id) {
        Optional<Task> rsl = Optional.empty();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query<Task> query = session.createQuery("from Task where id = :fId", Task.class)
                    .setParameter("fId", id);
            rsl = query.uniqueResultOptional();
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return rsl;
    }

    public boolean executeTask(int id) {
        int i = 0;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            i = session.createQuery("Update Task set done = true where id = :fId ")
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return i != 0;
    }

    public boolean deleteTask(int id) {
        int i = 0;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            i = session.createQuery("Delete Task where id = :fId ")
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return i != 0;
    }

    public boolean updateTask(int id, Task task) {
        int i = 0;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            i = session.createQuery("Update Task set description = :fDesc where id = :fId")
                    .setParameter("fDesc", task.getDescription())
                    .setParameter("fId", task.getId())
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return i != 0;
    }
}
