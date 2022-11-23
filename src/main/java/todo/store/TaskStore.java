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

    public List<Task> findAll() {
        List<Task> rsl = new ArrayList<>();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from Task order by id");
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
            Query query = session.createQuery("from Task as t where t.done = true order by id");
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
            Query query = session.createQuery("from Task as t where t.done = false order by id");
            for (Object o : query.list()) {
                rsl.add((Task) o);
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return rsl;
    }

    public Task findById(int id) {
        Task rsl = new Task();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from Task where id = :fId").setParameter("fId", id);
            rsl = (Task) query.uniqueResultOptional().get();
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return rsl;
    }

    public void executeTask(int id) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.createQuery("Update Task set done = true where id = :fId ")
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
    }

    public void deleteTask(int id) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.createQuery("Delete Task where id = :fId ")
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
    }

    public void updateTask(int id, Task task) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.createQuery("Update Task set description = :fDesc where id = :fId")
                    .setParameter("fDesc", task.getDescription())
                    .setParameter("fId", task.getId())
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
    }
}
