package ro.utcluj.Model.service;

import ro.utcluj.Model.model.Playlist;
import java.util.List;

public interface IPlaylistService {
    List<Playlist> viewAllPlaylistsForUser(int idUser);
    Playlist viewPlaylistForUserWithName(int idUser, String name);
    String createPlaylist(Playlist playlist);
}
