package ro.utcluj.Server.Repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ro.utcluj.ClientAndServer.Model.Song;
import ro.utcluj.ClientAndServer.Model.SongRatings;
import ro.utcluj.Server.ApplicationSession;

import java.util.List;

public class SongRatingsRepositoryImpl implements ISongRatingsRepository {

    public SongRatingsRepositoryImpl() {
    }

    @Override
    public List<SongRatings> viewAllSongRatingsForUser(int idUser) {
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        List<SongRatings> songRatings = null;
        String viewAllSongRatingsForUserQuery = "from SongRatings where idUser=" + idUser;
        try {
            session.beginTransaction();
            songRatings = session.createQuery(viewAllSongRatingsForUserQuery, SongRatings.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return songRatings;
    }

    @Override
    public List<SongRatings> viewAllSongRatingsForSong(int idSong) {
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        List<SongRatings> songRatings = null;
        String viewAllSongRatingsForSongQuery = "from SongRatings where idSong=" + idSong;
        try {
            session.beginTransaction();
            songRatings = session.createQuery(viewAllSongRatingsForSongQuery, SongRatings.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return songRatings;
    }

    @Override
    public int addSongRating(SongRatings songRatings) {
        int success = 1;
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.getTransaction();
            session.save(songRatings);
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
