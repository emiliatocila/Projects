package ro.utcluj.Model.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ro.utcluj.ApplicationSession;
import ro.utcluj.Model.model.PlaylistSongs;
import ro.utcluj.Model.model.Song;
import java.util.List;

public class PlaylistSongsRepositoryImpl implements IPlaylistSongsRepository {

    public PlaylistSongsRepositoryImpl(){
    }


    @Override
    public List<Song> viewAllSongsFromPlaylist(int idPlaylist) {
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        List<Song> songs = null;
        String viewAllSongsFromPlaylistQuery = "from Song where id in (select idSong from PlaylistSongs where idPlaylist=" + idPlaylist + ")";
        try {
            session.beginTransaction();
            songs = session.createQuery(viewAllSongsFromPlaylistQuery, Song.class).list();
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
    public int addSong(PlaylistSongs playlistSong) {
        int success = 1;
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.getTransaction();
            session.save(playlistSong);
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
    public int removeSong(int idPlaylist, int idSong) {
        int success = 1;
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.getTransaction();
            List<PlaylistSongs> playlistSongs = session.createQuery("from PlaylistSongs where idPlaylist=" + idPlaylist + " and idSong=" + idSong, PlaylistSongs.class).list();
            session.delete(playlistSongs.get(0));
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
    public boolean songExists(PlaylistSongs playlistSong) {
        SessionFactory sessionFactory = ApplicationSession.getSession();
        Session session = sessionFactory.openSession();
        List<PlaylistSongs> song = null;
        int idPlaylist = playlistSong.getIdPlaylist();
        int idSong = playlistSong.getIdSong();
        String songExistentQuery = "from PlaylistSongs where idPlaylist=" + idPlaylist + " and idSong=" + idSong;
        try {
            session.beginTransaction();
            song = session.createQuery(songExistentQuery, PlaylistSongs.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        if (song.isEmpty())
            return false;
        else
            return true;
    }
}
