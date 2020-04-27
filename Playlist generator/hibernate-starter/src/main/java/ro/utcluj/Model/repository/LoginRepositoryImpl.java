package ro.utcluj.Model.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ro.utcluj.ApplicationSession;
import ro.utcluj.Model.model.User;
import java.util.List;

public class LoginRepositoryImpl implements ILoginRepository {

    public LoginRepositoryImpl(){
    }

    @Override
    public User getUser(String username, String password){
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        User user = null;
        String getUserQuery = "from User where username='" + username + "' and password='" + password + "'";
        try {
            session.beginTransaction();
            session.getTransaction();
            List<User> users = session.createQuery(getUserQuery, User.class).list();
            session.getTransaction().commit();
            if (!users.isEmpty()){
                user = users.get(0);
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return user;
    }
}
