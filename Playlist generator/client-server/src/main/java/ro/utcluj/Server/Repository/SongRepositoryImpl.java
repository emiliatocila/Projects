package ro.utcluj.Server.Repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ro.utcluj.Server.ApplicationSession;
import ro.utcluj.ClientAndServer.Model.Song;
import java.util.List;

public class SongRepositoryImpl implements ISongRepository {

    public SongRepositoryImpl(){
    }

    @Override
    public List<Song> viewAllSongs() {
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        List<Song> songs = null;
        String viewAllSongsQuery = "from Song";
        try {
            session.beginTransaction();
            songs = session.createQuery(viewAllSongsQuery, Song.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return songs;
    }

    @Override
    public List<Song> viewAllSongsByTitle(String title) {
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        List<Song> songs = null;
        String viewAllSongsQuery = "from Song where title='" + title + "'";
        try {
            session.beginTransaction();
            songs = session.createQuery(viewAllSongsQuery, Song.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return songs;
    }

    @Override
    public List<Song> viewAllSongsByArtist(String artist) {
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        List<Song> songs = null;
        String viewAllSongsQuery = "from Song where artist='" + artist + "'";
        try {
            session.beginTransaction();
            songs = session.createQuery(viewAllSongsQuery, Song.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return songs;
    }

    @Override
    public List<Song> viewAllSongsByAlbum(String album) {
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        List<Song> songs = null;
        String viewAllSongsQuery = "from Song where album ='" + album + "'";
        try {
            session.beginTransaction();
            songs = session.createQuery(viewAllSongsQuery, Song.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return songs;
    }

    @Override
    public List<Song> viewAllSongsByGenre(String genre) {
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        List<Song> songs = null;
        String viewAllSongsQuery = "from Song where genre ='" + genre + "'";
        try {
            session.beginTransaction();
            songs = session.createQuery(viewAllSongsQuery, Song.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return songs;
    }

    @Override
    public List<Song> viewAllSongsByTopViews() {
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        List<Song> songs = null;
        String viewAllSongsQuery = "from Song where viewCount=(select max(viewCount) from Song)";
        try {
            session.beginTransaction();
            songs = session.createQuery(viewAllSongsQuery, Song.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return songs;
    }

    @Override
    public int insertSong(Song song) {
        int success = 1;
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.getTransaction();
            session.save(song);
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
    public int deleteSong(int id) {
        int success = 1;
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.getTransaction();
            List<Song> songs = session.createQuery("from Song where id=" + id, Song.class).list();
            session.delete(songs.get(0));
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
    public int updateSong(int id, String newTitle, String newArtist, String newAlbum, String newGenre, int newViewCount) {
        int success = 1;
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.getTransaction();
            List<Song> songs = session.createQuery("from Song where id=" + id, Song.class).list();
            Song song = songs.get(0);
            song.setTitle(newTitle);
            song.setArtist(newArtist);
            song.setAlbum(newAlbum);
            song.setGenre(newGenre);
            song.setViewCount(newViewCount);
            session.update(song);
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
    public int playSong(int id) {
        int success = 1;
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.getTransaction();
            List<Song> songs = session.createQuery("from Song where id=" + id, Song.class).list();
            Song song = songs.get(0);
            song.setViewCount(song.getViewCount() + 1);
            session.update(song);
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
