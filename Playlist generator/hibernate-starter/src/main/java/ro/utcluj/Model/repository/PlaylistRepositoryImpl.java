package ro.utcluj.Model.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ro.utcluj.ApplicationSession;
import ro.utcluj.Model.model.Playlist;
import java.util.List;

public class PlaylistRepositoryImpl implements IPlaylistRepository {

    public PlaylistRepositoryImpl(){
    }

    @Override
    public List<Playlist> viewAllPlaylistsForUser(int idUser) {
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        List<Playlist> playlists = null;
        String viewAllPlaylistsQuery = "from Playlist where idUser=" + idUser;
        try {
            session.beginTransaction();
            playlists = session.createQuery(viewAllPlaylistsQuery, Playlist.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return playlists;
    }

    @Override
    public List<Playlist> getPlaylistForUserWithName(int idUser, String name) {
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        List<Playlist> playlists = null;
        String viewAllPlaylistsQuery = "from Playlist where idUser=" + idUser + " and name='" + name + "'";
        try {
            session.beginTransaction();
            playlists = session.createQuery(viewAllPlaylistsQuery, Playlist.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return playlists;
    }

    @Override
    public int createPlaylist(Playlist playlist) {
        int success = 1;
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.getTransaction();
            session.save(playlist);
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
