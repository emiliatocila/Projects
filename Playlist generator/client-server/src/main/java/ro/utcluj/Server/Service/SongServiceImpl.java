package ro.utcluj.Server.Service;

import ro.utcluj.ClientAndServer.Model.Song;
import ro.utcluj.Server.Repository.ISongRepository;
import java.util.List;

public class SongServiceImpl implements ISongService {
    private ISongRepository songRepository;
    private IPlayedSongsService playedSongsService;

    public SongServiceImpl(ISongRepository songRepository, IPlayedSongsService playedSongsService) {
        this.songRepository = songRepository;
        this.playedSongsService = playedSongsService;
    }

    @Override
    public List<Song> viewAllSongs() {
        List<Song> songs = songRepository.viewAllSongs();
        return songs;
    }

    @Override
    public Song getSongById(int id) {
        return songRepository.getSongById(id);
    }

    @Override
    public List<Song> viewAllSongsByTitle(String title) {
        List<Song> songs = songRepository.viewAllSongsByTitle(title);
        return songs;
    }

    @Override
    public List<Song> viewAllSongsByArtist(String artist) {
        List<Song> songs = songRepository.viewAllSongsByArtist(artist);
        return songs;
    }

    @Override
    public List<Song> viewAllSongsByAlbum(String album) {
        List<Song> songs = songRepository.viewAllSongsByAlbum(album);
        return songs;
    }

    @Override
    public List<Song> viewAllSongsByGenre(String genre) {
        List<Song> songs = songRepository.viewAllSongsByGenre(genre);
        return songs;
    }

    @Override
    public List<Song> viewAllSongsByTopViews() {
        List<Song> songs = songRepository.viewAllSongsByTopViews();
        return songs;
    }

    @Override
    public List<Song> viewAllSongsByRating() {
        return songRepository.viewAllSongsByRating();
    }

    @Override
    public String insertSong(String title, String artist, String album, String genre, int viewCount, double rating) {
        if (title.length() > 0) {
            if (artist.length() > 0) {
                if (album.length() > 0) {
                    if (genre.length() > 0) {
                        if (viewCount > -1) {
                            if (songRepository.insertSong(new Song(title, artist, album, genre, viewCount, rating)) == -1)
                                return "Cannot create song!";
                            else return "Song created successfully!";
                        } else return "View count must be a positive number!";
                    } else return "Genre must be at least 1 character long!";
                } else return "Album name must be at least 1 character long!";
            } else return "Artist name must be at least 1 character long!";
        } else return "Title must be at least 1 character long!";
    }

    @Override
    public String deleteSong(int id) {
        if (songRepository.deleteSong(id) == -1)
            return "Cannot delete song!";
        else
            return "Song deleted successfully!";
    }

    @Override
    public String updateSong(int id, String newTitle, String newArtist, String newAlbum, String newGenre, int viewCount, double newRating) {
        if (newTitle.length() > 0) {
            if (newArtist.length() > 0) {
                if (newAlbum.length() > 0) {
                    if (newGenre.length() > 0) {
                        if (viewCount > -1) {
                            if (newRating >= 1.0 && newRating <= 5.0) {
                                if (songRepository.updateSong(id, newTitle, newArtist, newAlbum, newGenre, viewCount, newRating) == -1)
                                    return "Cannot update song!";
                                else return "Song updated successfully!";
                            } else return "Rating must be between 1 - 5 stars!";
                        } else return "View count must be a positive number!";
                    } else return "Genre must be at least 1 character long!";
                } else return "Album name must be at least 1 character long!";
            } else return "Artist name must be at least 1 character long!";
        } else return "Title must be at least 1 character long!";
    }

    @Override
    public String playSong(int idUser, int idSong) {
        if (songRepository.playSong(idSong) == -1)
            return "Cannot play song!";
        else {
            String addedToHistory = playedSongsService.addPlayedSong(idUser, idSong);
            if(addedToHistory.equals("Played song added to history!"))
                return "Song played!";
            else
                return "Song played but not added to history!";
        }
    }

}
