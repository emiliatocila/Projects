package ro.utcluj.ClientAndServer.Communication;

import com.google.gson.Gson;
import ro.utcluj.Client.Client;
import ro.utcluj.ClientAndServer.Model.*;
import ro.utcluj.Server.Repository.*;
import ro.utcluj.Server.Service.*;
import java.util.List;
import java.util.Map;

public class RequestHandler implements  IRequestHandler {
    private final ILoginService loginService;
    private final IPlaylistService playlistService;
    private final IPlaylistSongsService playlistSongsService;
    private final IFriendsService friendsService;
    private final IUserService userService;
    private final ISongService songService;
    private final ISongSuggService songSuggService;
    private final IPlayedSongsService playedSongsService;
    private final ISongRatingsService songRatingsService;

    public RequestHandler(){
        ILoginRepository loginRepository = new LoginRepositoryImpl();
        IUserRepository userRepository = new UserRepositoryImpl();
        ISongRepository songRepository = new SongRepositoryImpl();
        IPlaylistRepository playlistRepository = new PlaylistRepositoryImpl();
        IPlaylistSongsRepository playlistSongsRepository = new PlaylistSongsRepositoryImpl();
        IFriendsRepository friendsRepository = new FriendsRepositoryImpl();
        ISongSuggRepository songSuggRepository = new SongSuggRepositoryImpl();
        IPlayedSongsRepository playedSongsRepository = new PlayedSongsRepositoryImpl();
        ISongRatingsRepository songRatingsRepository = new SongRatingsRepositoryImpl();
        loginService = new LoginServiceImpl(loginRepository);
        userService = new UserServiceImpl(userRepository);
        playlistSongsService = new PlaylistSongsServiceImpl(playlistSongsRepository);
        playlistService = new PlaylistServiceImpl(playlistRepository, playlistSongsService);
        friendsService = new FriendsServiceImpl(friendsRepository, userRepository);
        songSuggService = new SongSuggServiceImpl(songSuggRepository, userRepository);
        playedSongsService = new PlayedSongsServiceImpl(playedSongsRepository);
        songRatingsService = new SongRatingsServiceImpl(songRatingsRepository);
        songService = new SongServiceImpl(songRepository, playedSongsService);
    }

    public RequestHandler(LiveNotificationHandler liveNotificationHandler){
        ILoginRepository loginRepository = new LoginRepositoryImpl();
        IUserRepository userRepository = new UserRepositoryImpl();
        ISongRepository songRepository = new SongRepositoryImpl();
        IPlaylistRepository playlistRepository = new PlaylistRepositoryImpl();
        IPlaylistSongsRepository playlistSongsRepository = new PlaylistSongsRepositoryImpl();
        IFriendsRepository friendsRepository = new FriendsRepositoryImpl();
        ISongSuggRepository songSuggRepository = new SongSuggRepositoryImpl();
        IPlayedSongsRepository playedSongsRepository = new PlayedSongsRepositoryImpl();
        ISongRatingsRepository songRatingsRepository = new SongRatingsRepositoryImpl();
        loginService = new LoginServiceImpl(loginRepository);
        userService = new UserServiceImpl(userRepository);
        playlistSongsService = new PlaylistSongsServiceImpl(playlistSongsRepository);
        playlistService = new PlaylistServiceImpl(playlistRepository, playlistSongsService);
        friendsService = new FriendsServiceImpl(friendsRepository, userRepository);
        songSuggService = new SongSuggServiceImpl(songSuggRepository, userRepository);
        songSuggService.addObserver(liveNotificationHandler);
        playedSongsService = new PlayedSongsServiceImpl(playedSongsRepository);
        songRatingsService = new SongRatingsServiceImpl(songRatingsRepository);
        songService = new SongServiceImpl(songRepository, playedSongsService);
    }

    public String encodeRequest(String operation, String params) {
        String encodedMessage = "";
        encodedMessage += operation + "_" + params;
        return encodedMessage;
    }

    public String encodeRequest(String operation, String param, Object o) {
        Gson gson = new Gson();
        String encodedMessage = "";
        encodedMessage += operation + "_" + param + gson.toJson(o) + "#";
        return encodedMessage;
    }

    public RequestMessage decodeRequest(String encodedMessage) {
        RequestMessage requestMessage = new RequestMessage();
        String[] splitEncodedMessage = encodedMessage.split("_");
        requestMessage.setOperation(splitEncodedMessage[0]);
        if(splitEncodedMessage.length > 1) {
            String[] splitParams = splitEncodedMessage[1].split("#");
            for (String param : splitParams) {
                String[] splitKeyVal = param.split("=");
                if(splitKeyVal.length == 1)
                    requestMessage.getParamValues().put(splitKeyVal[0], "");
                else
                    requestMessage.getParamValues().put(splitKeyVal[0], splitKeyVal[1]);
            }
        }
        return requestMessage;
    }

    public String handleRequest(String encodedMessage) {
        String responseMessage = "";
        RequestMessage requestMessage = decodeRequest(encodedMessage);
        String operation = requestMessage.getOperation();
        Map<String, String> paramValues = requestMessage.getParamValues();
        User user;
        Playlist playlist;
        List<User> users;
        List<Song> songs;
        Song song;
        List<Playlist> playlists;
        List<SongSugg> songSuggs;
        List<SongRatings> ratedSongs;
        String message = "";
        Gson gson = new Gson();
        switch(operation) {
            case "LOGIN":
                user = loginService.userLogin(paramValues.get("username"), paramValues.get("password"));
                responseMessage += gson.toJson(user);
                break;
            case "SHOWALLUSERS":
                users = userService.viewAllRegUsers();
                for(User u : users) {
                    responseMessage += gson.toJson(u) + "_";
                }
                break;
            case "SHOWALLSONGS":
                songs = songService.viewAllSongs();
                for(Song s : songs) {
                    responseMessage += gson.toJson(s) + "_";
                }
                break;
            case "GETSONGWITHID":
                song = songService.getSongById(Integer.parseInt(paramValues.get("id")));
                responseMessage += gson.toJson(song);
                break;
            case "SHOWALLPLAYEDSONGS":
                songs = playedSongsService.getPlayedSongs(Integer.parseInt(paramValues.get("idUser")));
                for(Song s : songs) {
                    responseMessage += gson.toJson(s) + "_";
                }
                break;
            case "INSERTUSER":
                message = userService.insertRegUser(paramValues.get("username"), paramValues.get("password"), 0);
                responseMessage += gson.toJson(message);
                break;
            case "INSERTSONG":
                message = songService.insertSong(paramValues.get("title"), paramValues.get("artist"), paramValues.get("album"),
                        paramValues.get("genre"), 0, 0);
                responseMessage += gson.toJson(message);
                break;
            case "DELETEUSER":
                message = userService.deleteRegUser(Integer.parseInt(paramValues.get("id")));
                responseMessage += gson.toJson(message);
                break;
            case "DELETESONG":
                message = songService.deleteSong(Integer.parseInt(paramValues.get("id")));
                responseMessage += gson.toJson(message);
                break;
            case "UPDATEUSER":
                message = userService.updateRegUser(Integer.parseInt(paramValues.get("id")), paramValues.get("newUsername"));
                responseMessage += gson.toJson(message);
                break;
            case "UPDATESONG":
                message = songService.updateSong(Integer.parseInt(paramValues.get("id")), paramValues.get("newTitle"), paramValues.get("newArtist"),
                        paramValues.get("newAlbum"), paramValues.get("newGenre"), Integer.parseInt(paramValues.get("newViewCount")), Double.parseDouble(paramValues.get("newRating")));
                responseMessage += gson.toJson(message);
                break;
            case "SHOWALLPLAYLISTS":
                playlists = playlistService.viewAllPlaylistsForUser(Integer.parseInt(paramValues.get("idUser")));
                for(Playlist p : playlists) {
                    responseMessage += gson.toJson(p) + "_";
                }
                break;
            case "DELETEPLAYLIST":
                message = playlistService.deletePlaylist(Integer.parseInt(paramValues.get("idPlaylist")));
                responseMessage += gson.toJson(message);
                break;
            case "SHOWPLAYLISTFORUSERWITHNAME":
                 playlist = playlistService.viewPlaylistForUserWithName(Integer.parseInt(paramValues.get("idUser")), paramValues.get("playlistName"));
                 responseMessage += gson.toJson(playlist);
                 break;
            case "SEARCHBYTITLE":
                songs = songService.viewAllSongsByTitle(paramValues.get("title"));
                for(Song s : songs) {
                    responseMessage += gson.toJson(s) + "_";
                }
                break;
            case "SEARCHBYARTIST":
                songs = songService.viewAllSongsByArtist(paramValues.get("artist"));
                for(Song s : songs) {
                    responseMessage += gson.toJson(s) + "_";
                }
                break;
            case "SEARCHBYALBUM":
                songs = songService.viewAllSongsByAlbum(paramValues.get("album"));
                for(Song s : songs) {
                    responseMessage += gson.toJson(s) + "_";
                }
                break;
            case "SEARCHBYGENRE":
                songs = songService.viewAllSongsByGenre(paramValues.get("genre"));
                for(Song s : songs) {
                    responseMessage += gson.toJson(s) + "_";
                }
                break;
            case "SEARCHBYTOPVIEWS":
                songs = songService.viewAllSongsByTopViews();
                for(Song s : songs) {
                    responseMessage += gson.toJson(s) + "_";
                }
                break;
            case "SEARCHBYRATING":
                songs = songService.viewAllSongsByRating();
                for(Song s : songs) {
                    responseMessage += gson.toJson(s) + "_";
                }
                break;
            case "CREATEPLAYLIST":
                message = playlistService.createPlaylist(Integer.parseInt(paramValues.get("idUser")), paramValues.get("playlistName"));
                responseMessage += gson.toJson(message);
                break;
            case "ADDSONG":
                message = playlistSongsService.addSong(gson.fromJson(paramValues.get("playlistSong"), PlaylistSongs.class));
                responseMessage += gson.toJson(message);
                break;
            case "SHOWALLSONGSUGGSSENT":
                songSuggs = songSuggService.viewAllSongSuggSent(Integer.parseInt(paramValues.get("idMe")));
                for (SongSugg songSugg : songSuggs) {
                    responseMessage += gson.toJson(songSugg) + "_";
                }
                break;
            case "SHOWALLSONGSUGGSRECEIVED":
                songSuggs = songSuggService.viewAllSongSuggReceived(Integer.parseInt(paramValues.get("idMe")));
                for (SongSugg songSugg : songSuggs) {
                    responseMessage += gson.toJson(songSugg) + "_";
                }
                break;
            case "GETSUGGSONG":
                song = songSuggService.getSuggSong(gson.fromJson(paramValues.get("songSugg"), SongSugg.class));
                responseMessage += gson.toJson(song);
                break;
            case "GETUSERNAMEWHOSUGGESTED":
                message = songSuggService.getUsernameWhoSuggested(gson.fromJson(paramValues.get("songSugg"), SongSugg.class));
                responseMessage += gson.toJson(message);
                break;
            case "ADDSONGSUGG":
                message += songSuggService.addSongSugg(Integer.parseInt(paramValues.get("idMe")), paramValues.get("usernameTo"), Integer.parseInt(paramValues.get("idSong")));
                responseMessage += gson.toJson(message);
                break;
            case "CONFIRMSONGSUGG":
                message += songSuggService.confirmSongSugg(Integer.parseInt(paramValues.get("id")));
                responseMessage += gson.toJson(message);
                break;
            case "DENYSONGSUGG":
                message += songSuggService.deleteSongSugg(Integer.parseInt(paramValues.get("id")));
                responseMessage += gson.toJson(message);
                break;
            case "SHOWALLPLAYLISTSONGS":
                songs = playlistSongsService.viewAllSongsFromPlaylist(Integer.parseInt(paramValues.get("idPlaylist")));
                for(Song s : songs) {
                    responseMessage += gson.toJson(s) + "_";
                }
                break;
            case "REMOVESONGFROMPLAYLIST":
                message = playlistSongsService.removeSong(Integer.parseInt(paramValues.get("idPlaylist")), Integer.parseInt(paramValues.get("idSong")));
                responseMessage += gson.toJson(message);
                break;
            case "PLAYSONG":
                message = songService.playSong(Integer.parseInt(paramValues.get("idUser")), Integer.parseInt(paramValues.get("idSong")));
                responseMessage += gson.toJson(message);
                break;
            case "RATESONG":
                message = songRatingsService.addSongRating(Integer.parseInt(paramValues.get("idUser")), Integer.parseInt(paramValues.get("idSong")), Integer.parseInt(paramValues.get("stars")));
                responseMessage += gson.toJson(message);
                break;
            case "GETRATEDSONGSFORUSER":
                ratedSongs = songRatingsService.viewAllSongRatingsForUser(Integer.parseInt(paramValues.get("idUser")));
                for(SongRatings songRating : ratedSongs) {
                    responseMessage += gson.toJson(songRating) + "_";
                }
                break;
            case "GETRATINGSFORSONG":
                ratedSongs = songRatingsService.viewAllSongRatingsForSong(Integer.parseInt(paramValues.get("idSong")));
                for(SongRatings songRating : ratedSongs) {
                    responseMessage += gson.toJson(songRating) + "_";
                }
                break;
            case "SHOWALLFRIENDS":
                users = friendsService.viewAllFriends(Integer.parseInt(paramValues.get("idMe")));
                for(User u : users) {
                    responseMessage += gson.toJson(u) + "_";
                }
                break;
            case "SHOWALLFRIENDREQUESTS":
                users = friendsService.viewAllFriendRequests(Integer.parseInt(paramValues.get("idMe")));
                for(User u : users) {
                    responseMessage += gson.toJson(u) + "_";
                }
                break;
            case "SHOWALLPENDINGFRIENDREQUESTS":
                users = friendsService.viewAllPendingFriendRequests(Integer.parseInt(paramValues.get("idMe")));
                for(User u : users) {
                    responseMessage += gson.toJson(u) + "_";
                }
                break;
            case "ADDFRIEND":
                message = friendsService.addFriend(paramValues.get("newFriendUsername"), Integer.parseInt(paramValues.get("idMe")));
                responseMessage += gson.toJson(message);
                break;
            case "CONFIRMFRIENDREQUEST":
                message = friendsService.confirmFriendRequest(paramValues.get("username"), Integer.parseInt(paramValues.get("idMe")));
                responseMessage += gson.toJson(message);
                break;
            case "DENYFRIENDREQUEST":
            case "REMOVEFRIEND":
                message = friendsService.deleteFriend(paramValues.get("username"), Integer.parseInt(paramValues.get("idMe")), Integer.parseInt(paramValues.get("fr")));
                responseMessage += gson.toJson(message);
                break;
        }
        return responseMessage;
    }

    public String sendRequestToServer(String encodedRequest) {
        return Client.getConnection().sendRequestToServer(encodedRequest);
    }

    public <T extends Object> List<T> decodeResponse(String encodedMessage, Class<T> tclass) {
        ResponseMessage responseMessage = new ResponseMessage(encodedMessage);
        return responseMessage.getDecodedObject(tclass);
    }

    @Override
    public <T> List<T> getResult(String operation, String params, Class<T> tclass) {
        String encodedRequest = this.encodeRequest(operation, params);
        String encodedResponse = this.sendRequestToServer(encodedRequest);
        return this.decodeResponse(encodedResponse, tclass);
    }

    @Override
    public <T> List<T> getResult(String operation, String params, Object o, Class<T> tclass) {
        String encodedRequest = this.encodeRequest(operation, params, o);
        String encodedResponse = this.sendRequestToServer(encodedRequest);
        return this.decodeResponse(encodedResponse, tclass);
    }

}
