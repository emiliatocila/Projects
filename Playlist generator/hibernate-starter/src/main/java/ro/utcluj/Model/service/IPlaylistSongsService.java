package ro.utcluj.Model.service;

import ro.utcluj.Model.model.PlaylistSongs;
import ro.utcluj.Model.model.Song;
import java.util.List;

public interface IPlaylistSongsService {
    List<Song> viewAllSongsFromPlaylist(int idPlaylist);
    String addSong(PlaylistSongs playlistSong);
    String removeSong(int idPlaylist, int idSong);
    boolean songExists(PlaylistSongs playlistSong);
}
