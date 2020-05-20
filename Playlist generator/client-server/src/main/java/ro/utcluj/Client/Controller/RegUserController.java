package ro.utcluj.Client.Controller;

import ro.utcluj.Client.View.LoginView;
import ro.utcluj.ClientAndServer.Communication.IRequestHandler;
import ro.utcluj.ClientAndServer.Model.*;
import ro.utcluj.ClientAndServer.Communication.RequestHandler;
import ro.utcluj.Client.View.IRegularView;
import javax.swing.*;
import java.util.*;

public class RegUserController implements Observer {
    private final IRequestHandler requestHandler;
    private final IRegularView regView;
    private List<Song> songs;
    private List<Playlist> playlists;
    private List<Song> playlistSongs;
    private List<User> friends;
    private List<User> friendRequests;
    private List<User> pendingFriendRequests;
    private List<SongSugg> songSuggsSent;
    private List<SongSugg> songSuggsReceived;
    private List<Song> suggSongsReceived;
    private List<String> whoSuggested;
    private List<Song> playedSongs;
    private List<SongRatings> ratedSongs;
    private List<SongRatings> ratingsForSong;
    private int okToConfirm = 0;


    public RegUserController(IRegularView regView) {
        this.regView = regView;
        requestHandler = new RequestHandler();
        this.initFriendsFriendRequestsSongsAndPlaylists();
    }

    public RegUserController(IRegularView regView, IRequestHandler requestHandler) {
        this.regView = regView;
        this.requestHandler = requestHandler;
    }

    public void initFriendsFriendRequestsSongsAndPlaylists() {
        this.initFriends();
        this.initFriendRequests();
        this.initPendingFriendRequests();
        this.initSongs();
        this.initSongSuggs();
        this.initPlaylists();
        this.initPlayedSongs();
        this.initRatedSongs();
        regView.init();
    }

    public void initSongSuggs(){
        this.initSongSuggsSent();
        this.initSongSuggsReceived();
        this.initSuggSongsReceived();
        this.initWhoSuggested();
    }

    public void initFriends() {
        int idMe = regView.getUserId();
        String params = "";
        params += "idMe=" + idMe + "#";

        friends = requestHandler.getResult("SHOWALLFRIENDS", params, User.class);
        regView.setFriends(friends);
    }

    public void initPendingFriendRequests() {
        int idMe = regView.getUserId();
        String params = "";
        params += "idMe=" + idMe + "#";

        pendingFriendRequests = requestHandler.getResult("SHOWALLPENDINGFRIENDREQUESTS", params, User.class);
        regView.setPendingFriendRequests(pendingFriendRequests);
    }

    public void initFriendRequests() {
        int idMe = regView.getUserId();
        String params = "";
        params += "idMe=" + idMe + "#";

        friendRequests = requestHandler.getResult("SHOWALLFRIENDREQUESTS", params, User.class);
        regView.setFriendRequests(friendRequests);
    }

    public void initSongSuggsSent() {
        int idMe = regView.getUserId();
        String params = "";
        params += "idMe=" + idMe + "#";

        songSuggsSent = requestHandler.getResult("SHOWALLSONGSUGGSSENT", params, SongSugg.class);
        regView.setSongSuggSent(songSuggsSent);
    }

    public void initSongSuggsReceived() {
        int idMe = regView.getUserId();
        String params = "";
        params += "idMe=" + idMe + "#";

        songSuggsReceived = requestHandler.getResult("SHOWALLSONGSUGGSRECEIVED", params, SongSugg.class);
        regView.setSongSuggReceived(songSuggsReceived);
    }

    private void initSuggSongsReceived() {
        suggSongsReceived = new ArrayList<>();
        if (songSuggsReceived.get(0) == null) {
            suggSongsReceived.clear();
            suggSongsReceived.add(null);
        }
        else {
            for (SongSugg songSugg : songSuggsReceived) {
                String params = "";
                params += "songSugg=";

                Song suggSong = ( requestHandler.getResult("GETSUGGSONG", params, songSugg, Song.class)).get(0);
                suggSongsReceived.add(suggSong);
            }
        }
        regView.setSuggSongsReceived(suggSongsReceived);
    }

    private void initWhoSuggested() {
        whoSuggested = new ArrayList<String>();
        if (songSuggsReceived.get(0) == null) {
            whoSuggested.clear();
            suggSongsReceived.add(null);
        }
        else {
            for (SongSugg songSugg : songSuggsReceived) {
                String params = "";
                params += "songSugg=";

                String whoSugg = (requestHandler.getResult("GETUSERNAMEWHOSUGGESTED", params, songSugg, String.class)).get(0);
                whoSuggested.add(whoSugg);
            }
        }
        regView.setWhoSuggested(whoSuggested);
    }

    public void initSongs() {
        songs = requestHandler.getResult("SHOWALLSONGS", null, Song.class);
        regView.setSongs(songs);
    }

    public void initPlayedSongs() {
        int idMe = regView.getUserId();
        String params = "";
        params += "idUser=" + idMe + "#";

        playedSongs = requestHandler.getResult("SHOWALLPLAYEDSONGS", params, Song.class);
        regView.setPlayedSongs(playedSongs);
    }

    public void initRatedSongs() {
        int idMe = regView.getUserId();
        String params = "";
        params += "idUser=" + idMe + "#";

        ratedSongs = requestHandler.getResult("GETRATEDSONGSFORUSER", params, SongRatings.class);
        regView.setRatedSongs(ratedSongs);
    }

    public void initRatingsForSong() {
        int idSong = regView.getIdRateSong();
        String params = "";
        params += "idSong=" + idSong + "#";

        ratingsForSong = requestHandler.getResult("GETRATINGSFORSONG", params, SongRatings.class);
        regView.setRatingsForSong(ratingsForSong);
    }

    public void initPlaylists() {
        int idMe = regView.getUserId();
        String params = "";
        params += "idUser=" + idMe + "#";

        playlists = requestHandler.getResult("SHOWALLPLAYLISTS", params, Playlist.class);
        regView.setPlaylists(playlists);
    }

    public void initPlaylistSongs(int id) {
        String params = "";
        params += "idPlaylist=" + id + "#";

        playlistSongs = requestHandler.getResult("SHOWALLPLAYLISTSONGS", params, Song.class);
        regView.setPlaylistSongs(playlistSongs);
    }

    public void showAllFriends() {
        this.initFriends();
        regView.setFriendsOrFriendRequests(0);
        regView.clearMainPanel();
        regView.init();
    }

    public void showAllFriendRequests() {
        this.initFriendRequests();
        regView.setFriendsOrFriendRequests(1);
        regView.clearMainPanel();
        regView.init();
    }

    public void showAllPendingFriendRequests() {
        this.initPendingFriendRequests();
        regView.setFriendsOrFriendRequests(2);
        regView.clearMainPanel();
        regView.init();
    }

    public void showAllSongSuggReceived() {
        this.initSongSuggs();
        regView.setSongsOrSongSugg(1);
        regView.clearMainPanel();
        regView.init();
    }

    public void showAllSongs() {
        this.initSongs();
        regView.setSongsOrSongSugg(0);
        regView.clearMainPanel();
        regView.init();
    }

    public void logOut() {
        regView.setVisibleRegView(false);
        LoginView loginView = new LoginView();
        loginView.setVisible(true);
    }

    public void searchBySongs() {
        int[] searchByOptionPane = regView.showSearchByOptionPane();
        int option = searchByOptionPane[0];
        if (option == JOptionPane.OK_OPTION) {
            int criteriaIndex = searchByOptionPane[1];
            String criteria = regView.getCriteria();
            String params = "";
            switch (criteriaIndex) {
                case 0:
                    params += "title=" + criteria + "#";
                    songs = requestHandler.getResult("SEARCHBYTITLE", params, Song.class);
                    break;
                case 1:
                    params = "";
                    params += "artist=" + criteria + "#";

                    songs = requestHandler.getResult("SEARCHBYARTIST", params, Song.class);
                    break;
                case 2:
                    params = "";
                    params += "album=" + criteria + "#";

                    songs = requestHandler.getResult("SEARCHBYALBUM", params, Song.class);
                    break;
                case 3:
                    params = "";
                    params += "genre=" + criteria + "#";

                    songs = requestHandler.getResult("SEARCHBYGENRE", params, Song.class);
                    break;
                case 4:
                    songs = requestHandler.getResult("SEARCHBYTOPVIEWS", null, Song.class);
                    break;
                case 5:
                    songs = requestHandler.getResult("SEARCHBYRATING", null, Song.class);
                    break;
            }
            if (songs.get(0) == null) {
                regView.showMessage("There are no songs found!");
            }
            else {
                regView.setSongs(songs);
            }
            regView.clearMainPanel();
            regView.init();
        }
    }

    public void createNewPlaylist(SongSugg newSongSugg) {
        int option = regView.createNewPlaylistOptionPane(newSongSugg);
        String message = "";
        if (option == JOptionPane.OK_OPTION){
            int idUser = regView.getUserId();
            String playlistName = regView.getPlaylistName();
            List<Integer> idSongsForNewPlaylist = regView.getIdSongsForNewPlaylist();

            String params = "";
            params += "idUser=" + idUser + "#playlistName=" + playlistName + "#";

            message = (requestHandler.getResult("CREATEPLAYLIST", params, String.class)).get(0);

            if (idSongsForNewPlaylist != null) {

                int idPlaylist;

                params = "";
                params += "idUser=" + idUser + "#playlistName=" + playlistName + "#";

                idPlaylist = (requestHandler.getResult("SHOWPLAYLISTFORUSERWITHNAME", params, Playlist.class)).get(0).getId();

                for (int idSong : idSongsForNewPlaylist) {
                    PlaylistSongs playlistSong = new PlaylistSongs(idPlaylist, idSong);

                    params = "";
                    params += "playlistSong=";

                    requestHandler.getResult("ADDSONG", params, playlistSong, String.class);
                }
            }
            regView.showMessage(message);
            okToConfirm = 1;
        }
        else
            okToConfirm = 0;

        this.initPlaylists();
        regView.resetIdSongsForNewPlaylist();
        regView.clearMainPanel();
        regView.init();
    }

    public void addSongsToAnExistingPlaylist(SongSugg newSongSugg) {
        int idUser = regView.getUserId();
        List<Playlist> playlists;
        String params = "";
        params += "idUser=" + idUser + "#";

        playlists = requestHandler.getResult("SHOWALLPLAYLISTS", params, Playlist.class);

        Playlist selectedPlaylist = null;
        if (playlists.get(0) == null) {
            regView.showMessage("There are no playlists created for this user!");
        }
        else {
            List<Object> addSongsOptionPane = regView.addNewSongsToExistingPlaylistOptionPane(playlists, newSongSugg);
            List<Integer> idSongsForNewPlaylist = regView.getIdSongsForNewPlaylist();
            if (idSongsForNewPlaylist == null) {
                regView.showMessage("No songs selected!");
            }
            else {
                int option = (int)addSongsOptionPane.get(0);
                if (option == JOptionPane.OK_OPTION){
                    String selectedPlaylistName = addSongsOptionPane.get(1).toString();
                    params = "";
                    params += "idUser=" + idUser + "#playlistName=" + selectedPlaylistName + "#";

                    selectedPlaylist = (requestHandler.getResult("SHOWPLAYLISTFORUSERWITHNAME", params, Playlist.class)).get(0);

                    int duplicateSongs = 0;
                    for (int idSong : idSongsForNewPlaylist) {
                        PlaylistSongs playlistSong = new PlaylistSongs(selectedPlaylist.getId(), idSong);
                        params = "";
                        params += "playlistSong=";

                        String message = (requestHandler.getResult("ADDSONG", params, playlistSong, String.class)).get(0);

                        if (message.equals("Duplicate song not added!"))
                            duplicateSongs++;
                    }
                    this.initPlaylists();
                    if (selectedPlaylist.getId() == regView.getIdOfPlaylistToView())
                        this.initPlaylistSongs(selectedPlaylist.getId());
                    regView.resetIdSongsForNewPlaylist();
                    regView.clearMainPanel();
                    regView.init();
                    if (duplicateSongs == 0)
                        regView.showMessage("Song(s) added successfully!");
                    else if (duplicateSongs == idSongsForNewPlaylist.size())
                        regView.showMessage("Duplicate song(s) not added!");
                    else if (duplicateSongs > 0 && duplicateSongs < idSongsForNewPlaylist.size())
                        regView.showMessage("Song(s) added successfully! Duplicate song(s) not added!");
                    okToConfirm = 1;
                }
                else {
                    okToConfirm = 0;
                    regView.resetIdSongsForNewPlaylist();
                }
            }
        }
    }


    public void addSongSugg() {
        int idMe = regView.getUserId();
        List<User> friends;
        String params = "";
        params += "idMe=" + idMe + "#";

        friends = requestHandler.getResult("SHOWALLFRIENDS", params, User.class);

        if (friends.get(0) == null) {
            regView.showMessage("Friend list is empty!");
        }
        else {
            List<Object> addSongSuggOptionPane = regView.addSongSuggOptionPane(friends);
            int idNewSuggSong = regView.getNewIdSongSugg();
            if (idNewSuggSong == -1) {
                regView.showMessage("Select a song to be suggested!");
            }
            else {
                int option = (int)addSongSuggOptionPane.get(0);
                if (option == JOptionPane.OK_OPTION){
                    String selectedFriendUsername = addSongSuggOptionPane.get(1).toString();
                    params = "";
                    params += "idMe=" + idMe + "#usernameTo=" + selectedFriendUsername + "#idSong=" + idNewSuggSong + "#";

                    String message = (requestHandler.getResult("ADDSONGSUGG", params, String.class)).get(0);

                    this.initSongSuggsSent();
                    regView.clearMainPanel();
                    regView.init();
                    regView.showMessage(message);
                    regView.resetIdNewSongSugg();
                }
            }
        }
    }

    public void viewPlaylist() {
        regView.setPlaylistsOrPlaylistsSongs(1);
        int okPlaylist = regView.selectPlaylistToView();
        if (okPlaylist == -1)
            regView.showMessage("Select a playlist to view!");
        else {
            int idOfPlaylist = regView.getIdOfPlaylistToView();
            this.initPlaylistSongs(idOfPlaylist);
            regView.clearMainPanel();
            regView.init();
        }
    }

    public void deletePlaylist() {
        int ok = regView.deletePlaylist();
        if (ok == -1)
            regView.showMessage("Select a playlist to be deleted!");
        else {
            int idPlaylist = regView.getIdPlaylistToDelete();
            String params = "";
            params += "idPlaylist=" + idPlaylist + "#";

            String message = (requestHandler.getResult("DELETEPLAYLIST", params, String.class)).get(0);

            regView.showMessage(message);
            this.initPlaylists();
            regView.clearMainPanel();
            regView.init();
        }
    }

    public void viewAllPlaylists() {
        regView.setPlaylistsOrPlaylistsSongs(0);
        this.initPlaylists();
        regView.clearMainPanel();
        regView.init();
    }

    public void removeSongFromPlaylist() {
        int ok = regView.deleteSong();
        if (ok == -1)
            regView.showMessage("Select a song to be deleted!");
        else {
            int idPlaylist = regView.getIdPlaylistToDelete();
            int idSong = regView.getIdSongToDelete();

            String params = "";
            params += "idPlaylist=" + idPlaylist + "#idSong=" + idSong + "#";

            String message = (requestHandler.getResult("REMOVESONGFROMPLAYLIST", params, String.class)).get(0);

            regView.showMessage(message);
            this.initPlaylistSongs(regView.getIdOfPlaylistToView());
            regView.clearMainPanel();
            regView.init();
        }
    }

    public void playSong() {
        int ok = regView.playSong();
        if (ok == -1)
            regView.showMessage("Select a song to play!");
        else {
            int idUser = regView.getUserId();
            int idSong = regView.getIdToPlay();
            String params = "";
            params += "idUser=" + idUser + "#idSong=" + idSong + "#";

            String message = (requestHandler.getResult("PLAYSONG", params, String.class)).get(0);

            regView.showMessage(message);
            this.initPlaylistSongs(regView.getIdOfPlaylistToView());
            this.initSongs();
            regView.clearMainPanel();
            regView.init();
        }
    }

    public void addFriend() {
        int option = regView.addFriendsOptionPane();
        String message = "";
        String params = "";
        if (option == JOptionPane.OK_OPTION) {
            int idMe = regView.getUserId();
            String newFriendUsername = regView.getNewFriendUsername();

            if (!newFriendUsername.equals(regView.getUserUsername())) {
                params += "newFriendUsername=" + newFriendUsername + "#idMe=" + idMe + "#";
                message = (requestHandler.getResult("ADDFRIEND", params, String.class)).get(0);
            }
            else
                message = "Please select a username different to yours!";
            regView.showMessage(message);
            this.initFriends();
            regView.clearMainPanel();
            regView.init();
        }
    }

    public void confirmFriendRequest() {
        int ok = regView.confirmFriendRequest();
        if (ok == -1)
            regView.showMessage("Select a friend request to be confirmed!");
        else {
            int idMe = regView.getUserId();
            String username = regView.getUsernameFriendRequestToConfirm();
            String params = "";
            params += "username=" + username + "#idMe=" + idMe + "#";

            String message = (requestHandler.getResult("CONFIRMFRIENDREQUEST", params, String.class)).get(0);

            regView.showMessage(message);
            this.initFriends();
            this.initFriendRequests();
            regView.clearMainPanel();
            regView.init();
        }
    }

    public void denyFriendRequest() {
        int ok = regView.denyFriendRequest();
        if (ok == -1)
            regView.showMessage("Select a friend request to be denied!");
        else {
            String username = regView.getUsernameFriendRequestToDeny();
            int idMe = regView.getUserId();
            String params = "";
            params += "username=" + username + "#idMe=" + idMe + "#fr=1#";

            String message = (requestHandler.getResult("DENYFRIENDREQUEST", params, String.class)).get(0);

            regView.showMessage(message);
            this.initFriendRequests();
            regView.clearMainPanel();
            regView.init();
        }
    }

    public void unfriend() {
        int ok = regView.deleteFriend();
        if (ok == -1)
            regView.showMessage("Select a friend to be removed!");
        else {
            String username = regView.getUsernameFriendToDelete();
            int idMe = regView.getUserId();
            String params = "";
            params += "username=" + username + "#idMe=" + idMe + "#fr=0#";

            String message = (requestHandler.getResult("REMOVEFRIEND", params, String.class)).get(0);

            regView.showMessage(message);
            this.initFriends();
            regView.clearMainPanel();
            regView.init();
        }
    }

    public void confirmSongSugg(SongSugg newSongSugg) {
        if (playlists.get(0) == null)
            this.createNewPlaylist(newSongSugg);
        else {
            int option = regView.createNewPlaylistOrAddToExistingOptionPane();
            if (option == JOptionPane.YES_OPTION)
                this.createNewPlaylist(newSongSugg);
            else if (option == JOptionPane.NO_OPTION)
                this.addSongsToAnExistingPlaylist(newSongSugg);
        }

        if(okToConfirm == 1) {
            int id = regView.getIdSongSuggConfirmed();
            String params = "";
            params += "id=" + id + "#";

            String message = (requestHandler.getResult("CONFIRMSONGSUGG", params, String.class)).get(0);

            regView.showMessage(message);
            this.initSongSuggs();
            regView.resetIdSongsForNewPlaylist();
            regView.clearMainPanel();
            regView.init();
        }
    }

    public void denySongSugg(SongSugg newSongSugg) {
        int ok = regView.denySongSuggestion(newSongSugg);
        if (ok == -1)
            regView.showMessage("Select a song suggestion to be denied!");
        else {
            int id = regView.getIdSongSuggDenied();
            String params = "";
            params += "id=" + id + "#";

            String message = (requestHandler.getResult("DENYSONGSUGG", params, String.class)).get(0);

            regView.showMessage(message);
            this.initSongSuggs();
            regView.clearMainPanel();
            regView.init();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        SongSugg newSongSugg = ((SongSugg)arg);
        int idMe = regView.getUserId();
        if (newSongSugg.getIdUserTo() == idMe) {
            String params = "";
            params += "songSugg=";

            Song newSuggSong = (requestHandler.getResult("GETSUGGSONG", params, newSongSugg, Song.class)).get(0);

            String whoSugg = (requestHandler.getResult("GETUSERNAMEWHOSUGGESTED", params, newSongSugg,String.class)).get(0);

            int option = regView.liveNotificationOptionPane(newSuggSong, whoSugg);
            if (option == JOptionPane.YES_OPTION) {
                this.confirmSongSugg(newSongSugg);
            }
            else if (option == JOptionPane.NO_OPTION) {
                this.denySongSugg(newSongSugg);
            }
        }
    }

    public void generatePlaylist() {
        int idMe = regView.getUserId();
        int nrPersonalizedMix = regView.getNrPersonalizedMix();
        List<Song> songsForNewPlaylist = new ArrayList<Song>();
        List<Song> songsWithMostPlayedGenre = new ArrayList<Song>();
        List<Integer> idSongsForNewPlaylist = new ArrayList<Integer>();

        this.initPlayedSongs();

        if(playedSongs.get(0) != null) {
            int nrPlayedSongs = playedSongs.size();

            if(nrPlayedSongs >= 5) {
                for(int i = nrPlayedSongs - 1; i >= nrPlayedSongs - 5; i--) {
                    songsForNewPlaylist.add(playedSongs.get(i));
                }
            }
            else
                for(Song s : playedSongs) {
                    songsForNewPlaylist.add(s);
                }

            Map<String, Integer> nrSongsOfGenres = new HashMap<>();
            String mostPlayedGenre = "";
            int nrTimesPlayedGenre = 0;
            for(Song s : songsForNewPlaylist) {
                if(!nrSongsOfGenres.containsKey(s.getGenre()))
                    nrSongsOfGenres.put(s.getGenre(), 1);
                else
                    nrSongsOfGenres.replace(s.getGenre(), nrSongsOfGenres.get(s.getGenre()) + 1);
            }
            mostPlayedGenre += nrSongsOfGenres.keySet().toArray()[0];
            nrTimesPlayedGenre = nrSongsOfGenres.get(mostPlayedGenre);
            for(String genre : nrSongsOfGenres.keySet()) {
                if(nrSongsOfGenres.get(genre) > nrTimesPlayedGenre) {
                    mostPlayedGenre = genre;
                    nrTimesPlayedGenre = nrSongsOfGenres.get(genre);
                }
            }

            String params = "";

            params = "";
            params += "genre=" + mostPlayedGenre + "#";

            songsWithMostPlayedGenre = requestHandler.getResult("SEARCHBYGENRE", params, Song.class);
            Collections.shuffle(songsWithMostPlayedGenre);

            for(int i = 0; i <= songsWithMostPlayedGenre.size()/2; i++) {
                idSongsForNewPlaylist.add(songsWithMostPlayedGenre.get(i).getId());
            }

            String message;
            String playlistName = "";
            playlistName += "Personalized Mix " + nrPersonalizedMix;

            params = "";
            params += "idUser=" + idMe + "#playlistName=" + playlistName + "#";

            message = (requestHandler.getResult("CREATEPLAYLIST", params, String.class)).get(0);

            int idPlaylist;

            idPlaylist = (requestHandler.getResult("SHOWPLAYLISTFORUSERWITHNAME", params, Playlist.class)).get(0).getId();

            for (int idSong : idSongsForNewPlaylist) {
                PlaylistSongs playlistSong = new PlaylistSongs(idPlaylist, idSong);

                params = "";
                params += "playlistSong=";

                requestHandler.getResult("ADDSONG", params, playlistSong, String.class);
            }
            regView.showMessage(message);
            this.initPlaylists();
            regView.increaseNrPersonalizedMix();
            regView.clearMainPanel();
            regView.init();
        }
        else
            regView.showMessage("Cannot generate playlist! No history!");
    }

    public void rateSong() {
        int option = regView.ratingSystem();
        int idMe = regView.getUserId();
        int idSongToRate = regView.getIdRateSong();
        if(option == JOptionPane.YES_OPTION) {
            int starsRated = regView.getRating();
            if (starsRated != 0) {
                String message = "";

                String params = "";
                params += "idUser=" + idMe + "#idSong=" + idSongToRate + "#stars=" + starsRated + "#";

                message = (requestHandler.getResult("RATESONG", params, String.class)).get(0);


                this.initRatingsForSong();
                double newRating = regView.getNewRating();

                Song ratedSong;
                params = "";
                params += "id=" + idSongToRate + "#";

                ratedSong = (requestHandler.getResult("GETSONGWITHID", params, Song.class)).get(0);

                params = "";
                params += "id=" + idSongToRate + "#newTitle=" + ratedSong.getTitle()+ "#newArtist=" + ratedSong.getArtist() +
                        "#newAlbum=" + ratedSong.getAlbum() + "#newGenre=" + ratedSong.getGenre() +
                        "#newViewCount=" + ratedSong.getViewCount() + "#newRating=" + newRating + "#";

                requestHandler.getResult("UPDATESONG", params, String.class);

                regView.showMessage(message);
                regView.resetRateSong();
                this.initRatedSongs();
                this.initSongs();
                regView.setRatedSongs(ratedSongs);
                regView.setSongs(songs);
                regView.clearMainPanel();
                regView.init();

            } else {
                regView.showMessage("Please rate the song between 1 - 5 stars!");
                regView.resetRateSong();
            }
        }
        else if(option == -1) {
            regView.showMessage("Please select a song to rate!");
            regView.resetRateSong();
        }
        else if(option == -2) {
            regView.showMessage("You have already rated this song!");
            regView.resetRateSong();
        }
    }
}
