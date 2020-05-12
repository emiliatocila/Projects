package ro.utcluj.Server.Repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ro.utcluj.ClientAndServer.Model.Song;
import ro.utcluj.ClientAndServer.Model.SongSugg;
import ro.utcluj.Server.ApplicationSession;

import java.util.List;

public class SongSuggRepositoryImpl implements ISongSuggRepository {

    public SongSuggRepositoryImpl(){
    }

    @Override
    public List<SongSugg> viewAllSongSuggSent(int idMe) {
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        List<SongSugg> songSuggsSent = null;
        String viewAllSongSuggsSentQuery = "from SongSugg where confirmed=0 and idUserFrom=" + idMe;
        try {
            session.beginTransaction();
            songSuggsSent = session.createQuery(viewAllSongSuggsSentQuery, SongSugg.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return songSuggsSent;
    }

    @Override
    public List<SongSugg> viewAllSongSuggReceived(int idMe) {
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        List<SongSugg> songSuggsSent = null;
        String viewAllSongSuggsSentQuery = "from SongSugg where confirmed=0 and idUserTo=" + idMe;
        try {
            session.beginTransaction();
            songSuggsSent = session.createQuery(viewAllSongSuggsSentQuery, SongSugg.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return songSuggsSent;
    }

    @Override
    public Song getSuggSong(SongSugg songSugg) {
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        List<Song> suggSongs = null;
        String getSuggSongQuery = "from Song where id=" + songSugg.getIdSong();
        try {
            session.beginTransaction();
            suggSongs = session.createQuery(getSuggSongQuery, Song.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return suggSongs.get(0);
    }

    @Override
    public int addSongSugg(SongSugg songSugg) {
        int success = 1;
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.getTransaction();
            session.save(songSugg);
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
    public int confirmSongSugg(int id) {
        int success = 1;
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.getTransaction();
            List<SongSugg> songSuggs = session.createQuery("from SongSugg where id=" + id, SongSugg.class).list();
            SongSugg songSugg = songSuggs.get(0);
            songSugg.setConfirmed(1);
            session.update(songSugg);
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
    public int deleteSongSugg(int id) {
        int success = 1;
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.getTransaction();
            List<SongSugg> songSuggs = session.createQuery("from SongSugg where id=" + id, SongSugg.class).list();
            session.delete(songSuggs.get(0));
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
