package ro.utcluj.Model.repository;

import ro.utcluj.Model.model.PlaylistSongs;
import ro.utcluj.Model.model.Song;
import java.util.List;

public interface IPlaylistSongsRepository {
    List<Song> viewAllSongsFromPlaylist(int idPlaylist);
    int addSong(PlaylistSongs playlistSong);
    int removeSong(int idPlaylist, int idSong);
    boolean songExists(PlaylistSongs playlistSong);
}
