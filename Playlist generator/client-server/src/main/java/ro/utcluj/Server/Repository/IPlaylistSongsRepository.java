package ro.utcluj.Server.Repository;

import ro.utcluj.ClientAndServer.Model.PlaylistSongs;
import ro.utcluj.ClientAndServer.Model.Song;
import java.util.List;

public interface IPlaylistSongsRepository {
    List<Song> viewAllSongsFromPlaylist(int idPlaylist);
    int addSong(PlaylistSongs playlistSong);
    int removeSong(int idPlaylist, int idSong);
    boolean songExists(PlaylistSongs playlistSong);
}
