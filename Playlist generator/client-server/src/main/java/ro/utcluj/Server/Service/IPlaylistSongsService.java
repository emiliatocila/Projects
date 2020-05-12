package ro.utcluj.Server.Service;

import ro.utcluj.ClientAndServer.Model.PlaylistSongs;
import ro.utcluj.ClientAndServer.Model.Song;
import java.util.List;

public interface IPlaylistSongsService {
    List<Song> viewAllSongsFromPlaylist(int idPlaylist);
    String addSong(PlaylistSongs playlistSong);
    String removeSong(int idPlaylist, int idSong);
    boolean songExists(PlaylistSongs playlistSong);
}
