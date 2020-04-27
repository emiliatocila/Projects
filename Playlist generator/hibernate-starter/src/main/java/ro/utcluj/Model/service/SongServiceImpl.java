package ro.utcluj.Model.service;

import ro.utcluj.Model.model.Song;
import ro.utcluj.Model.repository.ISongRepository;

import java.util.List;

public class SongServiceImpl implements ISongService {
    private ISongRepository songRepository;

    public SongServiceImpl(ISongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public List<Song> viewAllSongs() {
        List<Song> songs = songRepository.viewAllSongs();
        return songs;
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
    public String insertSong(String title, String artist, String album, String genre, int viewCount) {
        if (title.length() > 0) {
            if (artist.length() > 0) {
                if (album.length() > 0) {
                    if (genre.length() > 0) {
                        if (viewCount > -1) {
                            if (songRepository.insertSong(new Song(title, artist, album, genre, viewCount)) == -1)
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
    public String updateSong(int id, String newTitle, String newArtist, String newAlbum, String newGenre, int viewCount) {
        if (newTitle.length() > 0) {
            if (newArtist.length() > 0) {
                if (newAlbum.length() > 0) {
                    if (newGenre.length() > 0) {
                        if (viewCount > -1) {
                            if (songRepository.updateSong(id, newTitle, newArtist, newAlbum, newGenre, viewCount) == -1)
                                return "Cannot update song!";
                            else return "Song updated successfully!";
                        } else return "View count must be a positive number!";
                    } else return "Genre must be at least 1 character long!";
                } else return "Album name must be at least 1 character long!";
            } else return "Artist name must be at least 1 character long!";
        } else return "Title must be at least 1 character long!";
    }

    @Override
    public String playSong(int id) {
        if (songRepository.playSong(id) == -1)
            return "Cannot play song!";
        else return "Song played!";
    }

}
