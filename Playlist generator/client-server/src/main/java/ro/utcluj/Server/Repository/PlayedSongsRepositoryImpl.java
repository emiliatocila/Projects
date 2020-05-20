package ro.utcluj.Server.Repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ro.utcluj.ClientAndServer.Model.PlayedSongs;
import ro.utcluj.ClientAndServer.Model.Song;
import ro.utcluj.Server.ApplicationSession;

import java.util.List;

public class PlayedSongsRepositoryImpl implements IPlayedSongsRepository {
    public PlayedSongsRepositoryImpl() {
    }

    @Override
    public List<PlayedSongs> viewAllPlayedSongsForUser(int idUser) {
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        List<PlayedSongs> playedSongs = null;
        String viewAllPlayedSongsQuery = "from PlayedSongs where idUser=" + idUser;
        try {
            session.beginTransaction();
            playedSongs = session.createQuery(viewAllPlayedSongsQuery, PlayedSongs.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return playedSongs;
    }

    @Override
    public Song getPlayedSong(PlayedSongs playedSong) {
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        List<Song> playedSongs = null;
        String getPlayedSongQuery = "from Song where id=" + playedSong.getIdSong();
        try {
            session.beginTransaction();
            playedSongs = session.createQuery(getPlayedSongQuery, Song.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return playedSongs.get(0);
    }

    @Override
    public int addPlayedSong(PlayedSongs playedSong) {
        int success = 1;
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.getTransaction();
            session.save(playedSong);
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
