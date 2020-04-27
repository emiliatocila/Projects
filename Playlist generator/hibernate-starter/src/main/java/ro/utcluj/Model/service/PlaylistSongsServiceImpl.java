package ro.utcluj.Model.service;

import ro.utcluj.Model.model.PlaylistSongs;
import ro.utcluj.Model.model.Song;
import ro.utcluj.Model.repository.IPlaylistSongsRepository;

import java.util.List;

public class PlaylistSongsServiceImpl implements IPlaylistSongsService {
    private IPlaylistSongsRepository playlistSongsRepository;

    public PlaylistSongsServiceImpl(IPlaylistSongsRepository playlistSongsRepository) { this.playlistSongsRepository = playlistSongsRepository;}

    @Override
    public List<Song> viewAllSongsFromPlaylist(int idPlaylist) {
        return playlistSongsRepository.viewAllSongsFromPlaylist(idPlaylist);
    }

    @Override
    public String addSong(PlaylistSongs playlistSong) {
        if (playlistSongsRepository.songExists(playlistSong) == false) {
            if (playlistSongsRepository.addSong(playlistSong) == -1)
                return "Cannot add song!";
            else return "Song added successfully!";
        }
        else
            return "Duplicate song not added!";
    }

    @Override
    public String removeSong(int idPlaylist, int idSong) {
        if (playlistSongsRepository.removeSong(idPlaylist, idSong) == -1)
            return "Cannot remove song from playlist!";
        else return "Song removed successfully from playlist!";
    }

    @Override
    public boolean songExists(PlaylistSongs playlistSong) {
        if (playlistSongsRepository.songExists(playlistSong) == true)
            return true;
        else
            return false;
    }
}
