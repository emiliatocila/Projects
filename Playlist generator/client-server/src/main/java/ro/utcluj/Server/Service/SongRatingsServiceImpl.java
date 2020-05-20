package ro.utcluj.Server.Service;

import ro.utcluj.ClientAndServer.Model.PlayedSongs;
import ro.utcluj.ClientAndServer.Model.Song;
import ro.utcluj.ClientAndServer.Model.SongRatings;
import ro.utcluj.Server.Repository.ISongRatingsRepository;

import java.util.ArrayList;
import java.util.List;

public class SongRatingsServiceImpl implements ISongRatingsService {
    private ISongRatingsRepository songRatingsRepository;

    public SongRatingsServiceImpl(ISongRatingsRepository songRatingsRepository) {
        this.songRatingsRepository = songRatingsRepository;
    }

    @Override
    public List<SongRatings> viewAllSongRatingsForUser(int idUser) {
        return songRatingsRepository.viewAllSongRatingsForUser(idUser);
    }

    @Override
    public List<SongRatings> viewAllSongRatingsForSong(int idSong) {
        return songRatingsRepository.viewAllSongRatingsForSong(idSong);
    }

    @Override
    public String addSongRating(int idUser, int idSong, int stars) {
        SongRatings songRating = new SongRatings(idUser, idSong, stars);
        if(songRatingsRepository.addSongRating(songRating) == -1)
            return "Cannot rate song!";
        else
            return "Song rated!";
    }
}
