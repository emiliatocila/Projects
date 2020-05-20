import org.junit.jupiter.api.Test;
import ro.utcluj.Client.Controller.RegUserController;
import ro.utcluj.ClientAndServer.Communication.IRequestHandler;
import ro.utcluj.ClientAndServer.Model.Song;
import ro.utcluj.Client.View.IRegularView;
import ro.utcluj.ClientAndServer.Model.User;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class RegUserControllerTests {

    @Test
    public void invalid_searchBySongs_showErrorMessage(){
        IRegularView regView = mock(IRegularView.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);

        when(regView.showSearchByOptionPane()).thenReturn(new int[]{JOptionPane.OK_OPTION, 0});
        when(regView.getCriteria()).thenReturn("title");

        List<Song> songs = new ArrayList<>();
        songs.add(null);

        when(requestHandler.getResult(eq("SEARCHBYTITLE"), eq("title=title#"), eq(Song.class))).thenReturn(songs);

        RegUserController controller = new RegUserController(regView, requestHandler);

        controller.searchBySongs();

        verify(regView).showMessage("There are no songs found!");
    }

    @Test
    public void valid_createPlaylist_showMessage(){
        IRegularView regView = mock(IRegularView.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);

        when(regView.createNewPlaylistOptionPane(eq(null))).thenReturn(JOptionPane.OK_OPTION);
        when(regView.getUserId()).thenReturn(0);
        when(regView.getPlaylistName()).thenReturn("name");
        when(regView.getIdSongsForNewPlaylist()).thenReturn(null);

        List<String> messages = new ArrayList<>();
        String message = "Playlist created successfully!";
        messages.add(message);

        when(requestHandler.getResult(eq("CREATEPLAYLIST"), eq("idUser=0#playlistName=name#"), eq(String.class))).thenReturn(messages);

        RegUserController controller = new RegUserController(regView, requestHandler);

        controller.createNewPlaylist(null);

        verify(regView).showMessage("Playlist created successfully!");
    }

    @Test
    public void valid_addSongSugg_showMessage(){
        IRegularView regView = mock(IRegularView.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);

        when(regView.getUserId()).thenReturn(0);

        List<User> friends = new ArrayList<>();
        User friend = new User("friend", "friend", 0);
        friends.add(friend);
        List<Object> addSongSuggOptionPane = new ArrayList<>();
        addSongSuggOptionPane.add(JOptionPane.OK_OPTION);
        addSongSuggOptionPane.add("name");
        List<String> messages = new ArrayList<>();
        String message = "Song suggestion sent!";
        messages.add(message);

        when(requestHandler.getResult(eq("SHOWALLFRIENDS"), eq("idMe=0#"), eq(User.class))).thenReturn(friends);
        when(regView.addSongSuggOptionPane(eq(friends))).thenReturn(addSongSuggOptionPane);
        when(regView.getNewIdSongSugg()).thenReturn(0);
        when(requestHandler.getResult(eq("ADDSONGSUGG"), eq("idMe=0#usernameTo=name#idSong=0#"), eq(String.class))).thenReturn(messages);

        RegUserController controller = new RegUserController(regView, requestHandler);

        controller.addSongSugg();

        verify(regView).showMessage("Song suggestion sent!");
    }

    @Test
    public void invalid_addSongSugg_showErrorMessage(){
        IRegularView regView = mock(IRegularView.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);

        when(regView.getUserId()).thenReturn(0);

        List<User> friends = new ArrayList<>();
        friends.add(null);

        when(requestHandler.getResult(eq("SHOWALLFRIENDS"), eq("idMe=0#"), eq(User.class))).thenReturn(friends);

        RegUserController controller = new RegUserController(regView, requestHandler);

        controller.addSongSugg();

        verify(regView).showMessage("Friend list is empty!");
    }

    @Test
    public void valid_removeSongFromPlaylist_showMessage(){
        IRegularView regView = mock(IRegularView.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);

        when(regView.deleteSong()).thenReturn(0);
        when(regView.getIdPlaylistToDelete()).thenReturn(0);
        when(regView.getIdSongToDelete()).thenReturn(0);

        List<String> messages = new ArrayList<>();
        String message = "Song deleted successfully!";
        messages.add(message);

        when(requestHandler.getResult(eq("REMOVESONGFROMPLAYLIST"), eq("idPlaylist=0#idSong=0#"), eq(String.class))).thenReturn(messages);

        RegUserController controller = new RegUserController(regView, requestHandler);

        controller.removeSongFromPlaylist();

        verify(regView).showMessage("Song deleted successfully!");
    }

    @Test
    public void invalid_removeSongFromPlaylist_showErrorMessage(){
        IRegularView regView = mock(IRegularView.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);

        when(regView.deleteSong()).thenReturn(-1);

        RegUserController controller = new RegUserController(regView, requestHandler);

        controller.removeSongFromPlaylist();

        verify(regView).showMessage("Select a song to be deleted!");
    }

    @Test
    public void valid_playSong_showMessage(){
        IRegularView regView = mock(IRegularView.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);

        when(regView.playSong()).thenReturn(0);
        when(regView.getUserId()).thenReturn(0);
        when(regView.getIdToPlay()).thenReturn(0);

        List<String> messages = new ArrayList<>();
        String message = "Song played successfully!";
        messages.add(message);

        when(requestHandler.getResult(eq("PLAYSONG"), eq("idUser=0#idSong=0#"), eq(String.class))).thenReturn(messages);

        RegUserController controller = new RegUserController(regView, requestHandler);

        controller.playSong();

        verify(regView).showMessage("Song played successfully!");
    }

    @Test
    public void invalid_playSong_showErrorMessage(){
        IRegularView regView = mock(IRegularView.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);

        when(regView.playSong()).thenReturn(-1);

        RegUserController controller = new RegUserController(regView, requestHandler);

        controller.playSong();

        verify(regView).showMessage("Select a song to play!");
    }

    @Test
    public void invalid_addFriend_showErrorMessage(){
        IRegularView regView = mock(IRegularView.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);

        when(regView.addFriendsOptionPane()).thenReturn(JOptionPane.OK_OPTION);
        when(regView.getUserId()).thenReturn(0);
        when(regView.getNewFriendUsername()).thenReturn("me");
        when(regView.getUserUsername()).thenReturn("me");

        RegUserController controller = new RegUserController(regView, requestHandler);

        controller.addFriend();

        verify(regView).showMessage("Please select a username different to yours!");
    }

    @Test
    public void valid_addFriend_showMessage(){
        IRegularView regView = mock(IRegularView.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);

        when(regView.addFriendsOptionPane()).thenReturn(JOptionPane.OK_OPTION);
        when(regView.getUserId()).thenReturn(0);
        when(regView.getNewFriendUsername()).thenReturn("notme");
        when(regView.getUserUsername()).thenReturn("me");

        List<String> messages = new ArrayList<>();
        String message = "Friend request sent!";
        messages.add(message);

        when(requestHandler.getResult(eq("ADDFRIEND"), eq("newFriendUsername=notme#idMe=0#"), eq(String.class))).thenReturn(messages);

        RegUserController controller = new RegUserController(regView, requestHandler);

        controller.addFriend();

        verify(regView).showMessage("Friend request sent!");
    }

    @Test
    public void invalid_confirmFriendRequest_showErrorMessage(){
        IRegularView regView = mock(IRegularView.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);

        when(regView.confirmFriendRequest()).thenReturn(-1);

        RegUserController controller = new RegUserController(regView, requestHandler);

        controller.confirmFriendRequest();

        verify(regView).showMessage("Select a friend request to be confirmed!");
    }

    @Test
    public void valid_confirmFriendRequest_showMessage(){
        IRegularView regView = mock(IRegularView.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);

        when(regView.confirmFriendRequest()).thenReturn(JOptionPane.OK_OPTION);
        when(regView.getUserId()).thenReturn(0);
        when(regView.getUsernameFriendRequestToConfirm()).thenReturn("notme");

        List<String> messages = new ArrayList<>();
        String message = "Friend request confirmed!";
        messages.add(message);

        when(requestHandler.getResult(eq("CONFIRMFRIENDREQUEST"), eq("username=notme#idMe=0#"), eq(String.class))).thenReturn(messages);

        RegUserController controller = new RegUserController(regView, requestHandler);

        controller.confirmFriendRequest();

        verify(regView).showMessage("Friend request confirmed!");
    }

    @Test
    public void invalid_denyFriendRequest_showErrorMessage(){
        IRegularView regView = mock(IRegularView.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);

        when(regView.denyFriendRequest()).thenReturn(-1);

        RegUserController controller = new RegUserController(regView, requestHandler);

        controller.denyFriendRequest();

        verify(regView).showMessage("Select a friend request to be denied!");
    }

    @Test
    public void valid_denyFriendRequest_showMessage(){
        IRegularView regView = mock(IRegularView.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);

        when(regView.confirmFriendRequest()).thenReturn(JOptionPane.OK_OPTION);
        when(regView.getUsernameFriendRequestToDeny()).thenReturn("notme");
        when(regView.getUserId()).thenReturn(0);

        List<String> messages = new ArrayList<>();
        String message = "Friend request denied!";
        messages.add(message);

        when(requestHandler.getResult(eq("DENYFRIENDREQUEST"), eq("username=notme#idMe=0#fr=1#"), eq(String.class))).thenReturn(messages);

        RegUserController controller = new RegUserController(regView, requestHandler);

        controller.denyFriendRequest();

        verify(regView).showMessage("Friend request denied!");
    }

    @Test
    public void invalid_unfriend_showErrorMessage(){
        IRegularView regView = mock(IRegularView.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);

        when(regView.deleteFriend()).thenReturn(-1);

        RegUserController controller = new RegUserController(regView, requestHandler);

        controller.unfriend();

        verify(regView).showMessage("Select a friend to be removed!");
    }

    @Test
    public void valid_unfriend_showMessage(){
        IRegularView regView = mock(IRegularView.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);

        when(regView.deleteFriend()).thenReturn(JOptionPane.OK_OPTION);
        when(regView.getUsernameFriendToDelete()).thenReturn("notme");
        when(regView.getUserId()).thenReturn(0);

        List<String> messages = new ArrayList<>();
        String message = "Friend removed!";
        messages.add(message);

        when(requestHandler.getResult(eq("REMOVEFRIEND"), eq("username=notme#idMe=0#fr=0#"), eq(String.class))).thenReturn(messages);

        RegUserController controller = new RegUserController(regView, requestHandler);

        controller.unfriend();

        verify(regView).showMessage("Friend removed!");
    }

    @Test
    public void invalid_rateSong_showErrorMessage() {
        IRegularView regView = mock(IRegularView.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);

        when(regView.ratingSystem()).thenReturn(-1);
        when(regView.getUserId()).thenReturn(0);
        when(regView.getIdRateSong()).thenReturn(0);

        RegUserController controller = new RegUserController(regView, requestHandler);

        controller.rateSong();

        verify(regView).showMessage("Please select a song to rate!");
    }

    @Test
    public void valid_rateSong_showMessage() {
        IRegularView regView = mock(IRegularView.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);

        when(regView.ratingSystem()).thenReturn(JOptionPane.OK_OPTION);
        when(regView.getUserId()).thenReturn(0);
        when(regView.getIdRateSong()).thenReturn(0);
        when(regView.getRating()).thenReturn(3);

        List<String> messages = new ArrayList<>();
        String message = "Song rated!";
        messages.add(message);

        when(requestHandler.getResult(eq("RATESONG"), eq("idUser=0#idSong=0#stars=3#"), eq(String.class))).thenReturn(messages);
        when(regView.getNewRating()).thenReturn(3.5);

        List<Song> songs = new ArrayList<>();
        Song song = new Song("newTitle", "newArtist", "newAlbum", "newGenre", 1, 4.0);
        songs.add(song);

        when(requestHandler.getResult(eq("GETSONGWITHID"), eq("id=0#"), eq(Song.class))).thenReturn(songs);

        RegUserController controller = new RegUserController(regView, requestHandler);

        controller.rateSong();

        verify(regView).showMessage("Song rated!");
    }

}
