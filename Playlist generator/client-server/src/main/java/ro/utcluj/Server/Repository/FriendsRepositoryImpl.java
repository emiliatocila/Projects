package ro.utcluj.Server.Repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ro.utcluj.ClientAndServer.Model.Friends;
import ro.utcluj.Server.ApplicationSession;
import java.util.List;

public class FriendsRepositoryImpl implements IFriendsRepository {

    public FriendsRepositoryImpl(){
    }

    @Override
    public List<Friends> viewAllFriendsRel(int idMe) {
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        List<Friends> friendsRel = null;
        String viewAllFriendsRelQuery = "from Friends where confirmed=1 and (idFriend1=" + idMe + " or idFriend2=" + idMe + ")";
        try {
            session.beginTransaction();
            friendsRel = session.createQuery(viewAllFriendsRelQuery, Friends.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return friendsRel;
    }

    public List<Friends> viewAllFriendsRelConfirmedOrNot(int idMe) {
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        List<Friends> friendsRel = null;
        String viewAllFriendsRelQuery = "from Friends where (idFriend1=" + idMe + " or idFriend2=" + idMe + ")";
        try {
            session.beginTransaction();
            friendsRel = session.createQuery(viewAllFriendsRelQuery, Friends.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return friendsRel;
    }

    @Override
    public List<Friends> viewAllPendingFriendRequests(int idMe) {
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        List<Friends> friendsRel = null;
        String viewAllFriendsRelQuery = "from Friends where confirmed=0 and idFriend1=" + idMe;
        try {
            session.beginTransaction();
            friendsRel = session.createQuery(viewAllFriendsRelQuery, Friends.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return friendsRel;
    }

    @Override
    public List<Friends> viewAllFriendRelRequests(int idMe) {
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        List<Friends> friendsRel = null;
        String viewAllFriendsRelQuery = "from Friends where confirmed=0 and idFriend2=" + idMe;
        try {
            session.beginTransaction();
            friendsRel = session.createQuery(viewAllFriendsRelQuery, Friends.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return friendsRel;
    }

    @Override
    public int addFriend(Friends friend) {
        int success = 1;
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.getTransaction();
            session.save(friend);
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

    @Override
    public int confirmFriendRequest(int id) {
        int success = 1;
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.getTransaction();
            List<Friends> friends = session.createQuery("from Friends where id=" + id, Friends.class).list();
            Friends friend = friends.get(0);
            friend.setConfirmed(1);
            session.update(friend);
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

    @Override
    public int deleteFriend(int id) {
        int success = 1;
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.getTransaction();
            List<Friends> friends = session.createQuery("from Friends where id=" + id, Friends.class).list();
            session.delete(friends.get(0));
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
