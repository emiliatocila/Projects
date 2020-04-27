package ro.utcluj.Model.service;

import ro.utcluj.Model.model.Playlist;
import ro.utcluj.Model.repository.IPlaylistRepository;

import java.util.List;

public class PlaylistServiceImpl implements IPlaylistService {
    private IPlaylistRepository playlistRepository;

    public PlaylistServiceImpl(IPlaylistRepository playlistRepository) { this.playlistRepository = playlistRepository;}

    @Override
    public List<Playlist> viewAllPlaylistsForUser(int idUser) {
        return playlistRepository.viewAllPlaylistsForUser(idUser);
    }

    @Override
    public Playlist viewPlaylistForUserWithName(int idUser, String name) {
        List<Playlist> playlists = playlistRepository.getPlaylistForUserWithName(idUser, name);
        if (playlists.isEmpty())
            return null;
        else return playlists.get(0);
    }

    @Override
    public String createPlaylist(Playlist playlist) {
        String name;
        name = playlist.getName();
        if (name.length() > 0) {
            if (playlistRepository.createPlaylist(playlist) == -1)
                return "Cannot create playlist!";
            else return "Playlist created successfully!";
        } else return "Playlist name must be at least 1 character long!";
    }
}
