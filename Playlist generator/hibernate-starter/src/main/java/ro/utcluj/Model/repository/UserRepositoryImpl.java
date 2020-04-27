package ro.utcluj.Model.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ro.utcluj.ApplicationSession;
import ro.utcluj.Model.model.User;
import java.util.List;

public class UserRepositoryImpl implements IUserRepository {

    public UserRepositoryImpl(){
    }

    @Override
    public List<User> viewAllRegUsers(){
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        List<User> regularUsers = null;
        String viewAllRegularUsersQuery = "from User where isAdmin=0";
        try {
            session.beginTransaction();
            regularUsers = session.createQuery(viewAllRegularUsersQuery, User.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return regularUsers;
    }

    @Override
    public int insertRegUser(User user) {
        int success = 1;
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.getTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            success = -1;
        } finally {
            session.close();
        }
        return success;
    }

    public int deleteRegUser(int id) {
        int success = 1;
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.getTransaction();
            List<User> users = session.createQuery("from User where id=" + id, User.class).list();
            session.delete(users.get(0));
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            success = -1;
        } finally {
            session.close();
        }
        return success;
    }

    public int updateRegUser(int id, String newUsername) {
        int success = 1;
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.getTransaction();
            List<User> users = session.createQuery("from User where id=" + id, User.class).list();
            User user = users.get(0);
            user.setUsername(newUsername);
            session.update(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            success = -1;
        } finally {
            session.close();
        }
        return success;
    }
}


