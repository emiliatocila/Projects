package ro.utcluj.Client.Controller;

import jdk.nashorn.internal.scripts.JO;
import ro.utcluj.Client.Client;
import ro.utcluj.Client.ClientConnectionToServer;
import ro.utcluj.Client.View.LoginView;
import ro.utcluj.ClientAndServer.Model.*;
import ro.utcluj.ClientAndServer.Communication.RequestHandler;
import ro.utcluj.Client.View.IRegularView;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class RegUserController implements Observer {
    private static ClientConnectionToServer connection;
    private final RequestHandler requestHandler;
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
    private int okToConfirm = 0;


    public RegUserController(IRegularView regView) {
        this.regView = regView;
        connection = Client.getConnection();
        requestHandler = new RequestHandler();
        this.initFriendsFriendRequestsSongsAndPlaylists();
    }

    public void initFriendsFriendRequestsSongsAndPlaylists() {
        this.initFriends();
        this.initFriendRequests();
        this.initPendingFriendRequests();
        this.initSongs();
        this.initSongSuggs();
        this.initPlaylists();
        regView.init();
    }

    public void initSongSuggs(){
        this.initSongSuggsSent();
        this.initSongSuggsReceived();
        this.initSuggSongsReceived();
        this.initWhoSuggested();
    }

    public void initFriends() {
        String encodedRequest = requestHandler.encodeRequest("SHOWALLFRIENDS", "idMe=" + regView.getUserId() + "#");
        String encodedResponse = connection.sendRequestToServer(encodedRequest);
        friends = (List<User>) requestHandler.decodeResponse(encodedResponse, User.class);
        regView.setFriends(friends);
    }

    public void initPendingFriendRequests() {
        String encodedRequest = requestHandler.encodeRequest("SHOWALLPENDINGFRIENDREQUESTS", "idMe=" + regView.getUserId() + "#");
        String encodedResponse = connection.sendRequestToServer(encodedRequest);
        pendingFriendRequests = (List<User>) requestHandler.decodeResponse(encodedResponse, User.class);
        regView.setPendingFriendRequests(pendingFriendRequests);
    }

    public void initFriendRequests() {
        String encodedRequest = requestHandler.encodeRequest("SHOWALLFRIENDREQUESTS", "idMe=" + regView.getUserId() + "#");
        String encodedResponse = connection.sendRequestToServer(encodedRequest);
        friendRequests = (List<User>) requestHandler.decodeResponse(encodedResponse, User.class);
        regView.setFriendRequests(friendRequests);
    }

    public void initSongSuggsSent() {
        String encodedRequest = requestHandler.encodeRequest("SHOWALLSONGSUGGSSENT", "idMe=" + regView.getUserId() + "#");
        String encodedResponse = connection.sendRequestToServer(encodedRequest);
        songSuggsSent = (List<SongSugg>) requestHandler.decodeResponse(encodedResponse, SongSugg.class);
        regView.setSongSuggSent(songSuggsSent);
    }

    public void initSongSuggsReceived() {
        String encodedRequest = requestHandler.encodeRequest("SHOWALLSONGSUGGSRECEIVED", "idMe=" + regView.getUserId() + "#");
        String encodedResponse = connection.sendRequestToServer(encodedRequest);
        songSuggsReceived = (List<SongSugg>) requestHandler.decodeResponse(encodedResponse, SongSugg.class);
        regView.setSongSuggReceived(songSuggsReceived);
    }

    private void initSuggSongsReceived() {
        suggSongsReceived = new ArrayList<Song>();
        if (songSuggsReceived.get(0) == null) {
            suggSongsReceived.clear();
            suggSongsReceived.add(null);
        }
        else {
            for (SongSugg songSugg : songSuggsReceived) {
                String encodedRequest = requestHandler.encodeRequest("GETSUGGSONG", "songSugg=", songSugg);
                String encodedResponse = connection.sendRequestToServer(encodedRequest);
                Song suggSong = ((List<Song>) requestHandler.decodeResponse(encodedResponse, Song.class)).get(0);
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
                String encodedRequest = requestHandler.encodeRequest("GETUSERNAMEWHOSUGGESTED", "songSugg=", songSugg);
                String encodedResponse = connection.sendRequestToServer(encodedRequest);
                String whoSugg = ((List<String>) requestHandler.decodeResponse(encodedResponse, String.class)).get(0);
                whoSuggested.add(whoSugg);
            }
        }
        regView.setWhoSuggested(whoSuggested);
    }

    public void initSongs() {
        String encodedRequest = requestHandler.encodeRequest("SHOWALLSONGS", null);
        String encodedResponse = connection.sendRequestToServer(encodedRequest);
        songs = (List<Song>) requestHandler.decodeResponse(encodedResponse, Song.class);
        regView.setSongs(songs);
    }

    public void initPlaylists() {
        String encodedRequest = requestHandler.encodeRequest("SHOWALLPLAYLISTS", "idUser=" + regView.getUserId() + "#");
        String encodedResponse = connection.sendRequestToServer(encodedRequest);
        playlists = (List<Playlist>) requestHandler.decodeResponse(encodedResponse, Playlist.class);
        regView.setPlaylists(playlists);
    }

    public void initPlaylistSongs(int id) {
        String encodedRequest = requestHandler.encodeRequest("SHOWALLPLAYLISTSONGS", "idPlaylist=" + id + "#");
        String encodedResponse = connection.sendRequestToServer(encodedRequest);
        playlistSongs = (List<Song>) requestHandler.decodeResponse(encodedResponse, Song.class);
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
            String encodedRequest = "";
            String encodedResponse = "";
            switch (criteriaIndex) {
                case 0:
                    encodedRequest = requestHandler.encodeRequest("SEARCHBYTITLE", "title=" + criteria + "#");
                    encodedResponse = connection.sendRequestToServer(encodedRequest);
                    songs = (List<Song>) requestHandler.decodeResponse(encodedResponse, Song.class);
                    break;
                case 1:
                    encodedRequest = requestHandler.encodeRequest("SEARCHBYARTIST", "artist=" + criteria + "#");
                    encodedResponse = connection.sendRequestToServer(encodedRequest);
                    songs = (List<Song>) requestHandler.decodeResponse(encodedResponse, Song.class);
                    break;
                case 2:
                    encodedRequest = requestHandler.encodeRequest("SEARCHBYALBUM", "album=" + criteria + "#");
                    encodedResponse = connection.sendRequestToServer(encodedRequest);
                    songs = (List<Song>) requestHandler.decodeResponse(encodedResponse, Song.class);
                    break;
                case 3:
                    encodedRequest = requestHandler.encodeRequest("SEARCHBYGENRE", "genre=" + criteria + "#");
                    encodedResponse = connection.sendRequestToServer(encodedRequest);
                    songs = (List<Song>) requestHandler.decodeResponse(encodedResponse, Song.class);
                    break;
                case 4:
                    encodedRequest = requestHandler.encodeRequest("SEARCHBYTOPVIEWS", null);
                    encodedResponse = connection.sendRequestToServer(encodedRequest);
                    songs = (List<Song>) requestHandler.decodeResponse(encodedResponse, Song.class);
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
        String encodedRequest = "";
        String encodedResponse = "";
        if (option == JOptionPane.OK_OPTION){
            int idUser = regView.getUserId();
            String playlistName = regView.getPlaylistName();
            Playlist playlist = new Playlist(idUser, playlistName);
            List<Integer> idSongsForNewPlaylist = regView.getIdSongsForNewPlaylist();

            encodedRequest = requestHandler.encodeRequest("CREATEPLAYLIST", "playlist=", playlist);
            encodedResponse = connection.sendRequestToServer(encodedRequest);
            message = ((List<String>) requestHandler.decodeResponse(encodedResponse, String.class)).get(0);

            if (idSongsForNewPlaylist != null) {

                int idPlaylist;

                encodedRequest = requestHandler.encodeRequest("SHOWPLAYLISTFORUSERWITHNAME", "idUser=" + idUser +
                        "#playlistName=" + playlistName + "#");
                encodedResponse = connection.sendRequestToServer(encodedRequest);
                idPlaylist = ((List<Playlist>) requestHandler.decodeResponse(encodedResponse, Playlist.class)).get(0).getId();

                for (int idSong : idSongsForNewPlaylist) {
                    PlaylistSongs playlistSong = new PlaylistSongs(idPlaylist, idSong);

                    encodedRequest = requestHandler.encodeRequest("ADDSONG", "playlistSong=", playlistSong);
                    encodedResponse = connection.sendRequestToServer(encodedRequest);
                    requestHandler.decodeResponse(encodedResponse, String.class);
                }
                this.initPlaylists();
                this.initPlaylistSongs(regView.getUserId());
                regView.resetIdSongsForNewPlaylist();
                regView.clearMainPanel();
                regView.init();
            }
            regView.showMessage(message);
            okToConfirm = 1;
        }
        else
            okToConfirm = 0;
    }

    public void addSongsToAnExistingPlaylist(SongSugg newSongSugg) {
        int idUser = regView.getUserId();
        List<Playlist> playlists;
        String encodedRequest;
        String encodedResponse;

        encodedRequest = requestHandler.encodeRequest("SHOWALLPLAYLISTS", "idUser=" + idUser + "#");
        encodedResponse = connection.sendRequestToServer(encodedRequest);
        playlists = (List<Playlist>) requestHandler.decodeResponse(encodedResponse, Playlist.class);

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

                    encodedRequest = requestHandler.encodeRequest("SHOWPLAYLISTFORUSERWITHNAME", "idUser=" + idUser +
                                    "#playlistName=" + selectedPlaylistName + "#");
                    encodedResponse = connection.sendRequestToServer(encodedRequest);
                    selectedPlaylist = ((List<Playlist>) requestHandler.decodeResponse(encodedResponse, Playlist.class)).get(0);

                    int duplicateSongs = 0;
                    for (int idSong : idSongsForNewPlaylist) {
                        PlaylistSongs playlistSong = new PlaylistSongs(selectedPlaylist.getId(), idSong);

                        encodedRequest = requestHandler.encodeRequest("ADDSONG", "playlistSong=", playlistSong);
                        encodedResponse = connection.sendRequestToServer(encodedRequest);
                        String message = ((List<String>) requestHandler.decodeResponse(encodedResponse, String.class)).get(0);

                        if (message.equals("Duplicate song not added!"))
                            duplicateSongs++;
                    }
                    this.initPlaylistSongs(selectedPlaylist.getId());
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
                else
                    okToConfirm = 0;
                regView.resetIdSongsForNewPlaylist();
            }
        }
    }


    public void addSongSugg() {
        int idMe = regView.getUserId();
        List<User> friends;
        String encodedRequest;
        String encodedResponse;

        encodedRequest = requestHandler.encodeRequest("SHOWALLFRIENDS", "idMe=" + idMe + "#");
        encodedResponse = connection.sendRequestToServer(encodedRequest);
        friends = (List<User>) requestHandler.decodeResponse(encodedResponse, User.class);

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

                    encodedRequest = requestHandler.encodeRequest("ADDSONGSUGG", "idMe=" + idMe + "#usernameTo=" + selectedFriendUsername +
                                "#idSong=" + idNewSuggSong + "#");
                    encodedResponse = connection.sendRequestToServer(encodedRequest);
                    String message = ((List<String>) requestHandler.decodeResponse(encodedResponse, String.class)).get(0);

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

            String encodedRequest = requestHandler.encodeRequest("DELETEPLAYLIST", "idPlaylist=" + idPlaylist + "#");
            String encodedResponse = connection.sendRequestToServer(encodedRequest);
            String message = ((List<String>) requestHandler.decodeResponse(encodedResponse, String.class)).get(0);

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

            String encodedRequest = requestHandler.encodeRequest("REMOVESONGFROMPLAYLIST", "idPlaylist=" + idPlaylist + "#idSong=" + idSong + "#");
            String encodedResponse = connection.sendRequestToServer(encodedRequest);
            String message = ((List<String>) requestHandler.decodeResponse(encodedResponse, String.class)).get(0);

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
            int id = regView.getIdToPlay();

            String encodedRequest = requestHandler.encodeRequest("PLAYSONG", "idSong=" + id + "#");
            String encodedResponse = connection.sendRequestToServer(encodedRequest);
            String message = ((List<String>) requestHandler.decodeResponse(encodedResponse, String.class)).get(0);

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
        String encodedRequest = "";
        String encodedResponse = "";
        if (option == JOptionPane.OK_OPTION) {
            int idMe = regView.getUserId();
            String newFriendUsername = regView.getNewFriendUsername();

            if (!newFriendUsername.equals(regView.getUserUsername())) {
                encodedRequest = requestHandler.encodeRequest("ADDFRIEND", "newFriendUsername=" + newFriendUsername +
                        "#idMe=" + idMe + "#");
                encodedResponse = connection.sendRequestToServer(encodedRequest);
                message = ((List<String>) requestHandler.decodeResponse(encodedResponse, String.class)).get(0);
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
            String username = regView.getUsernameFriendRequestToConfirm();

            String encodedRequest = requestHandler.encodeRequest("CONFIRMFRIENDREQUEST", "username=" + username +
                            "#idMe=" + regView.getUserId() + "#");
            String encodedResponse = connection.sendRequestToServer(encodedRequest);
            String message = ((List<String>) requestHandler.decodeResponse(encodedResponse, String.class)).get(0);

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

            String encodedRequest = requestHandler.encodeRequest("DENYFRIENDREQUEST", "username=" + username +
                    "#idMe=" + regView.getUserId() + "#fr=1#");
            String encodedResponse = connection.sendRequestToServer(encodedRequest);
            String message = ((List<String>) requestHandler.decodeResponse(encodedResponse, String.class)).get(0);

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

            String encodedRequest = requestHandler.encodeRequest("REMOVEFRIEND", "username=" + username +
                    "#idMe=" + regView.getUserId() + "#fr=0#");
            String encodedResponse = connection.sendRequestToServer(encodedRequest);
            String message = ((List<String>) requestHandler.decodeResponse(encodedResponse, String.class)).get(0);

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
            String encodedRequest = requestHandler.encodeRequest("CONFIRMSONGSUGG", "id=" + regView.getIdSongSuggConfirmed() + "#");
            String encodedResponse = connection.sendRequestToServer(encodedRequest);
            String message = ((List<String>) requestHandler.decodeResponse(encodedResponse, String.class)).get(0);

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

            String encodedRequest = requestHandler.encodeRequest("DENYSONGSUGG", "id=" + id + "#");
            String encodedResponse = connection.sendRequestToServer(encodedRequest);
            String message = ((List<String>) requestHandler.decodeResponse(encodedResponse, String.class)).get(0);

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

            String encodedRequest = requestHandler.encodeRequest("GETSUGGSONG", "songSugg=", newSongSugg);
            String encodedResponse = connection.sendRequestToServer(encodedRequest);
            Song newSuggSong = ((List<Song>) requestHandler.decodeResponse(encodedResponse, Song.class)).get(0);

            encodedRequest = requestHandler.encodeRequest("GETUSERNAMEWHOSUGGESTED", "songSugg=", newSongSugg);
            encodedResponse = connection.sendRequestToServer(encodedRequest);
            String whoSugg = ((List<String>) requestHandler.decodeResponse(encodedResponse, String.class)).get(0);

            int option = regView.liveNotificationOptionPane(newSuggSong, whoSugg);
            if (option == JOptionPane.YES_OPTION) {
                this.confirmSongSugg(newSongSugg);
            }
            else if (option == JOptionPane.NO_OPTION) {
                this.denySongSugg(newSongSugg);
            }
        }
    }

}
