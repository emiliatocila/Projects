package ro.utcluj.Model.repository;

import ro.utcluj.Model.model.Playlist;
import java.util.List;

public interface IPlaylistRepository {
    List<Playlist> viewAllPlaylistsForUser(int idUser);
    List<Playlist> getPlaylistForUserWithName(int idUser, String name);
    int createPlaylist(Playlist playlist);
}
