package ro.utcluj.Server.Service;

import ro.utcluj.ClientAndServer.Model.Playlist;
import ro.utcluj.ClientAndServer.Model.Song;
import ro.utcluj.Server.Repository.IPlaylistRepository;
import java.util.List;

public class PlaylistServiceImpl implements IPlaylistService {
    private IPlaylistRepository playlistRepository;
    private IPlaylistSongsService playlistSongsService;

    public PlaylistServiceImpl(IPlaylistRepository playlistRepository, IPlaylistSongsService playlistSongsService) {
        this.playlistRepository = playlistRepository;
        this.playlistSongsService = playlistSongsService;
    }

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
    public String createPlaylist(int idUser, String name) {
        if (name.length() > 0) {
            if (playlistRepository.createPlaylist(new Playlist(idUser, name)) == -1)
                return "Cannot create playlist!";
            else return "Playlist created successfully!";
        } else return "Playlist name must be at least 1 character long!";
    }

    @Override
    public String deletePlaylist(int idPlaylist) {
        List<Song> songsFromPlaylist = playlistSongsService.viewAllSongsFromPlaylist(idPlaylist);
        for (Song s : songsFromPlaylist) {
            playlistSongsService.removeSong(idPlaylist, s.getId());
        }
        if (playlistRepository.deletePlaylist(idPlaylist) == -1)
            return "Cannot delete playlist!";
        else return "Playlist deteled successfully!";
    }
}
