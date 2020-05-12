import org.junit.jupiter.api.Test;
import ro.utcluj.Client.Controller.RegUserController;
import ro.utcluj.ClientAndServer.Model.Song;
import ro.utcluj.Server.Service.IPlaylistService;
import ro.utcluj.Server.Service.IPlaylistSongsService;
import ro.utcluj.Server.Service.ISongService;
import ro.utcluj.Client.View.IRegularView;
import javax.swing.*;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class RegUserControllerTests {

    /*@Test
    public void invalid_searchBySongs_showErrorMessage(){
        IRegularView regView = mock(IRegularView.class);
        ISongService songService = mock(ISongService.class);
        IPlaylistService playlistService = mock(IPlaylistService.class);
        IPlaylistSongsService playlistSongsService = mock(IPlaylistSongsService.class);
        when(regView.showSearchByOptionPane()).thenReturn(new int[]{JOptionPane.OK_OPTION, 0});
        when(regView.getCriteria()).thenReturn("title");
        when(songService.viewAllSongsByTitle(eq("title"))).thenReturn(new ArrayList<Song>());

        RegUserController controller = new RegUserController(regView, songService, playlistService, playlistSongsService);

        controller.searchBySongs();

        verify(regView).showMessage("There are no songs found!");
    }

    @Test
    public void invalid_createPlaylist_showErrorMessage(){
        IRegularView regView = mock(IRegularView.class);
        ISongService songService = mock(ISongService.class);
        IPlaylistService playlistService = mock(IPlaylistService.class);
        IPlaylistSongsService playlistSongsService = mock(IPlaylistSongsService.class);
        when(regView.createNewPlaylistOptionPane()).thenReturn(JOptionPane.OK_OPTION);
        when(regView.getUserId()).thenReturn(0);
        when(regView.getPlaylistName()).thenReturn("name");
        when(regView.getIdSongsForNewPlaylist()).thenReturn(null);

        RegUserController controller = new RegUserController(regView, songService, playlistService, playlistSongsService);

        controller.createNewPlaylist();

        verify(regView).showMessage("Cannot create playlist! No songs selected!");
    }

    @Test
    public void valid_removeSongFromPlaylist_showMessage(){
        IRegularView regView = mock(IRegularView.class);
        ISongService songService = mock(ISongService.class);
        IPlaylistService playlistService = mock(IPlaylistService.class);
        IPlaylistSongsService playlistSongsService = mock(IPlaylistSongsService.class);
        when(regView.deleteSong()).thenReturn(0);
        when(regView.getIdPlaylistToDelete()).thenReturn(0);
        when(regView.getIdSongToDelete()).thenReturn(0);
        when(playlistSongsService.removeSong(eq(0), eq(0))).thenReturn("Song deleted successfully!");

        RegUserController controller = new RegUserController(regView, songService, playlistService, playlistSongsService);

        controller.removeSongFromPlaylist();

        verify(regView).showMessage("Song deleted successfully!");
    }

    @Test
    public void invalid_removeSongFromPlaylist_showErrorMessage(){
        IRegularView regView = mock(IRegularView.class);
        ISongService songService = mock(ISongService.class);
        IPlaylistService playlistService = mock(IPlaylistService.class);
        IPlaylistSongsService playlistSongsService = mock(IPlaylistSongsService.class);
        when(regView.deleteSong()).thenReturn(-1);

        RegUserController controller = new RegUserController(regView, songService, playlistService, playlistSongsService);

        controller.removeSongFromPlaylist();

        verify(regView).showMessage("Select a song to be deleted!");
    }

    @Test
    public void valid_playSong_showMessage(){
        IRegularView regView = mock(IRegularView.class);
        ISongService songService = mock(ISongService.class);
        IPlaylistService playlistService = mock(IPlaylistService.class);
        IPlaylistSongsService playlistSongsService = mock(IPlaylistSongsService.class);
        when(regView.playSong()).thenReturn(0);
        when(regView.getIdToPlay()).thenReturn(0);
        when(songService.playSong(eq(0))).thenReturn("Song played successfully!");

        RegUserController controller = new RegUserController(regView, songService, playlistService, playlistSongsService);

        controller.playSong();

        verify(regView).showMessage("Song played successfully!");
    }

    @Test
    public void invalid_playSong_showErrorMessage(){
        IRegularView regView = mock(IRegularView.class);
        ISongService songService = mock(ISongService.class);
        IPlaylistService playlistService = mock(IPlaylistService.class);
        IPlaylistSongsService playlistSongsService = mock(IPlaylistSongsService.class);
        when(regView.playSong()).thenReturn(-1);

        RegUserController controller = new RegUserController(regView, songService, playlistService, playlistSongsService);

        controller.playSong();

        verify(regView).showMessage("Select a song to play!");
    }*/

}
