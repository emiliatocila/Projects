package ro.utcluj.Server.Service;

import ro.utcluj.ClientAndServer.Model.PlayedSongs;
import ro.utcluj.ClientAndServer.Model.Song;
import ro.utcluj.Server.Repository.IPlayedSongsRepository;

import java.util.ArrayList;
import java.util.List;

public class PlayedSongsServiceImpl implements IPlayedSongsService {
    private IPlayedSongsRepository playedSongsRepository;

    public PlayedSongsServiceImpl(IPlayedSongsRepository playedSongsRepository) {
        this.playedSongsRepository = playedSongsRepository;
    }

    @Override
    public List<PlayedSongs> viewAllPlayedSongsForUser(int idUser) {
        return playedSongsRepository.viewAllPlayedSongsForUser(idUser);
    }

    @Override
    public Song getPlayedSong(PlayedSongs playedSong) {
        return playedSongsRepository.getPlayedSong(playedSong);
    }

    @Override
    public List<Song> getPlayedSongs(int idUser) {
        List<PlayedSongs> playedSongs = this.viewAllPlayedSongsForUser(idUser);
        List<Song> songs = new ArrayList<>();
        for (PlayedSongs playedSong : playedSongs) {
            songs.add(playedSongsRepository.getPlayedSong(playedSong));
        }
        return songs;
    }

    @Override
    public String addPlayedSong(int idUser, int idSong) {
        PlayedSongs playedSong = new PlayedSongs(idUser, idSong);
        if(playedSongsRepository.addPlayedSong(playedSong) == -1)
            return "Cannot add song to played songs history!";
        else
            return "Played song added to history!";
    }
}
