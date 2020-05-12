package ro.utcluj.Client.View;

import ro.utcluj.Client.Client;
import ro.utcluj.Client.Controller.RegUserController;
import ro.utcluj.ClientAndServer.Model.Playlist;
import ro.utcluj.ClientAndServer.Model.Song;
import ro.utcluj.ClientAndServer.Model.SongSugg;
import ro.utcluj.ClientAndServer.Model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.List;

public class RegularView extends JFrame implements IRegularView {

    User user = null;
    List<User> friends = new ArrayList<>();
    List<User> friendRequests = new ArrayList<>();
    List<User> pendingFriendRequests = new ArrayList<>();
    List<Song> songs = new ArrayList<Song>();
    List<Playlist> playlists = new ArrayList<Playlist>();
    List<Song> playlistSongs = new ArrayList<Song>();
    List<SongSugg> songSuggSent = new ArrayList<SongSugg>();
    List<SongSugg> songSuggReceived = new ArrayList<SongSugg>();
    List<Song> suggSongsReceived = new ArrayList<Song>();
    List<String> whoSuggested = new ArrayList<String>();

    JTabbedPane mainPanel;

    JPanel homePanel;
    JPanel songsPanel;
    JPanel playlistsPanel;

    JTable friendsTable;
    JTable friendRequestsTable;
    JTable pendingFriendRequestsTable;
    JTable songsTable;
    JTable playlistsTable;
    JTable playlistSongsTable;
    JTable songSuggReceivedTable;
    JScrollPane scrollFriends;
    JScrollPane scrollFriendRequests;
    JScrollPane scrollPendingFriendRequests;
    JScrollPane scrollSongs;
    JScrollPane scrollPlaylists;
    JScrollPane scrollPlaylistSongs;
    JScrollPane scrollSongSuggReceivedTable;

    private JButton showAllSongsBtn = new JButton("Show all songs");
    private JButton showAllSongs2Btn = new JButton("Back");
    private JButton searchBySongsBtn = new JButton("Search by criteria");
    private JButton createNewPlaylistBtn = new JButton("Create new playlist");
    private JButton addSongToAnExistingPlaylistBtn = new JButton("Add song(s) to an existing playlist");
    private JButton viewSongsFromPlaylistBtn = new JButton("View playlist");
    private JButton deletePlaylistBtn = new JButton("Delete playlist");
    private JButton viewPlaylistsBtn = new JButton("Back");
    private JButton removeSongFromPlaylistBtn = new JButton("Remove song from playlist");
    private JButton playSongBtn = new JButton("Play song");
    private JButton logOutBtn = new JButton("Log out");
    private JButton showAllFriendsBtn = new JButton("Back");
    private JButton showAllFriendRequestsBtn = new JButton("View friend requests");
    private JButton showAllPendingFriendRequestsBtn = new JButton("View pending friend requests");
    private JButton confirmFriendRequestBtn = new JButton("Confirm friend request");
    private JButton denyFriendRequestsBtn = new JButton("Deny friend request");
    private JButton addFriendBtn = new JButton("Add a new friend");
    private JButton deleteFriendBtn = new JButton("Unfriend");
    private JButton addSongSuggBtn = new JButton("Suggest song to friend");
    private JButton showAllSongSuggReceivedBtn = new JButton("View suggested songs");
    private JButton confirmSuggSongBtn = new JButton("Accept song suggestion");
    private JButton denySuggSongBtn = new JButton("Deny song suggestion");

    JTextField newFriendUsernameTextField;
    JTextField criteriaTextField;
    JTextField playlistNameTextField;

    private int playlistsOrPlaylistSongs = 0;
    private int friendsOrFriendRequests = 0;
    private int songsOrSongSugg = 0;
    private String selectedPlaylistName = null;
    private int idOfPlaylist = -1;
    private int idSongToDelete = -1;
    private int idPlaylistToDelete = -1;
    private List<Integer> idSongsForNewPlaylist;
    private int idToPlay = -1;
    private String usernameFriendRequestToConfirm = null;
    private String usernameFriendRequestToDeny = null;
    private String usernameFriendToDelete = null;
    private int idNewSongSugg = -1;
    private int idSongSuggConfirmed = -1;
    private int idSongSuggDenied = -1;

    public RegularView(User user){
        this.user = user;
        mainPanel = new JTabbedPane();
        this.setContentPane(mainPanel);
        this.pack();
        this.setSize(new Dimension(1100, 600));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Logged in as regular user - " + user.getUsername());

        RegUserController regUserController = new RegUserController(this);
        Client.setRegUserController(regUserController);

        showAllSongsBtn.addActionListener(e -> regUserController.showAllSongs());
        showAllSongs2Btn.addActionListener(e -> regUserController.showAllSongs());
        searchBySongsBtn.addActionListener(e -> regUserController.searchBySongs());
        createNewPlaylistBtn.addActionListener(e -> regUserController.createNewPlaylist(null));
        addSongToAnExistingPlaylistBtn.addActionListener(e -> regUserController.addSongsToAnExistingPlaylist(null));
        viewSongsFromPlaylistBtn.addActionListener(e -> regUserController.viewPlaylist());
        deletePlaylistBtn.addActionListener(e -> regUserController.deletePlaylist());
        viewPlaylistsBtn.addActionListener(e -> regUserController.viewAllPlaylists());
        removeSongFromPlaylistBtn.addActionListener(e -> regUserController.removeSongFromPlaylist());
        playSongBtn.addActionListener(e -> regUserController.playSong());
        logOutBtn.addActionListener(e -> regUserController.logOut());
        addFriendBtn.addActionListener(e -> regUserController.addFriend());
        showAllFriendsBtn.addActionListener(e -> regUserController.showAllFriends());
        showAllFriendRequestsBtn.addActionListener(e -> regUserController.showAllFriendRequests());
        showAllPendingFriendRequestsBtn.addActionListener(e -> regUserController.showAllPendingFriendRequests());
        confirmFriendRequestBtn.addActionListener(e -> regUserController.confirmFriendRequest());
        denyFriendRequestsBtn.addActionListener(e -> regUserController.denyFriendRequest());
        deleteFriendBtn.addActionListener(e -> regUserController.unfriend());
        addSongSuggBtn.addActionListener(e -> regUserController.addSongSugg());
        showAllSongSuggReceivedBtn.addActionListener(e -> regUserController.showAllSongSuggReceived());
        confirmSuggSongBtn.addActionListener(e -> regUserController.confirmSongSugg(null));
        denySuggSongBtn.addActionListener(e -> regUserController.denySongSugg(null));
    }

    @Override
    public void init() {
        initHome();
        initSongs();
        initPlaylists();
        mainPanel.addTab("Home", homePanel);
        mainPanel.addTab("Songs", songsPanel);
        mainPanel.addTab("Playlists", playlistsPanel);
    }

    public void initHome(){
        homePanel = new JPanel();
        homePanel.setLayout(new FlowLayout());
        JPanel friendsPanelB = new JPanel();
        friendsPanelB.setLayout(new BoxLayout(friendsPanelB, BoxLayout.Y_AXIS));
        if (friendsOrFriendRequests == 0) {
            if (!friends.isEmpty())
                this.createTableF();
            else {
                friendsPanelB.add(Box.createRigidArea(new Dimension(0, 120)));
                friendsPanelB.add(new JLabel("Friend list empty! Add a new friend!"));
                friendsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
            }
            friendsPanelB.add(logOutBtn);
            friendsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
            friendsPanelB.add(addFriendBtn);
            friendsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
            friendsPanelB.add(deleteFriendBtn);
            friendsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
            friendsPanelB.add(showAllFriendRequestsBtn);
            friendsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
            friendsPanelB.add(showAllPendingFriendRequestsBtn);
            friendsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
            homePanel.add(friendsPanelB);
            if (friends.isEmpty()) {
                deleteFriendBtn.setEnabled(false);
            } else {
                deleteFriendBtn.setEnabled(true);
            }
        } else if (friendsOrFriendRequests == 1) {
            JPanel friendRequestsPanelB = new JPanel();
            friendRequestsPanelB.setLayout(new BoxLayout(friendRequestsPanelB, BoxLayout.Y_AXIS));
            if (!friendRequests.isEmpty())
                this.createTableFR();
            else {
                friendRequestsPanelB.add(Box.createRigidArea(new Dimension(0, 120)));
                friendRequestsPanelB.add(new JLabel("There are no friend requests!"));
                friendRequestsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
            }
            friendRequestsPanelB.add(showAllFriendsBtn);
            friendRequestsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
            friendRequestsPanelB.add(confirmFriendRequestBtn);
            friendRequestsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
            friendRequestsPanelB.add(denyFriendRequestsBtn);
            friendRequestsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
            homePanel.add(friendRequestsPanelB);
            if (friendRequests.isEmpty()) {
                confirmFriendRequestBtn.setEnabled(false);
                denyFriendRequestsBtn.setEnabled(false);
            } else {
                confirmFriendRequestBtn.setEnabled(true);
                denyFriendRequestsBtn.setEnabled(true);
            }
        }
            else if (friendsOrFriendRequests == 2) {
            JPanel pendingFriendRequestsPanelB = new JPanel();
            pendingFriendRequestsPanelB.setLayout(new BoxLayout(pendingFriendRequestsPanelB, BoxLayout.Y_AXIS));
            if (!pendingFriendRequests.isEmpty())
                this.createTablePFR();
            else {
                pendingFriendRequestsPanelB.add(Box.createRigidArea(new Dimension(0, 120)));
                pendingFriendRequestsPanelB.add(new JLabel("There are no pending friend requests!"));
                pendingFriendRequestsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
            }
            pendingFriendRequestsPanelB.add(showAllFriendsBtn);
            pendingFriendRequestsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
            homePanel.add(pendingFriendRequestsPanelB);
        }
    }

    public void initSongs(){
        songsPanel = new JPanel();
        songsPanel.setLayout(new FlowLayout());
        if (songsOrSongSugg == 0) {
            JPanel songsPanelB = new JPanel();
            songsPanelB.setLayout(new BoxLayout(songsPanelB, BoxLayout.Y_AXIS));
            if (songs.isEmpty()) {
                songsPanelB.add(Box.createRigidArea(new Dimension(0, 120)));
                songsPanelB.add(new JLabel("There are no songs!"));
                songsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
            } else
                this.createTableS();
            songsPanelB.add(showAllSongsBtn);
            songsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
            songsPanelB.add(searchBySongsBtn);
            songsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
            songsPanelB.add(createNewPlaylistBtn);
            songsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
            songsPanelB.add(addSongToAnExistingPlaylistBtn);
            songsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
            songsPanelB.add(addSongSuggBtn);
            songsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
            songsPanelB.add(showAllSongSuggReceivedBtn);
            songsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
            songsPanel.add(songsPanelB);
            if (songs.isEmpty()) {
                searchBySongsBtn.setEnabled(false);
                createNewPlaylistBtn.setEnabled(false);
                addSongToAnExistingPlaylistBtn.setEnabled(false);
            } else {
                searchBySongsBtn.setEnabled(true);
                createNewPlaylistBtn.setEnabled(true);
                addSongToAnExistingPlaylistBtn.setEnabled(true);
            }
        } else if (songsOrSongSugg == 1) {
            JPanel songSuggsPanelB = new JPanel();
            songSuggsPanelB.setLayout(new BoxLayout(songSuggsPanelB, BoxLayout.Y_AXIS));
            if (songSuggReceived.isEmpty()) {
                songSuggsPanelB.add(Box.createRigidArea(new Dimension(0, 120)));
                songSuggsPanelB.add(new JLabel("There are no song suggestions!"));
                songSuggsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
            } else
                this.createTableSSR();
            songSuggsPanelB.add(showAllSongs2Btn);
            songSuggsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
            songSuggsPanelB.add(confirmSuggSongBtn);
            songSuggsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
            songSuggsPanelB.add(denySuggSongBtn);
            songSuggsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
            songsPanel.add(songSuggsPanelB);
            if (songSuggReceived.isEmpty()) {
                confirmSuggSongBtn.setEnabled(false);
                denySuggSongBtn.setEnabled(false);
            } else {
                confirmSuggSongBtn.setEnabled(true);
                denySuggSongBtn.setEnabled(true);
            }
        }
    }

    public void initPlaylists(){
        playlistsPanel = new JPanel();
        playlistsPanel.setLayout(new FlowLayout());
        JPanel playlistsPanelB = new JPanel();
        playlistsPanelB.setLayout(new BoxLayout(playlistsPanelB, BoxLayout.Y_AXIS));
        if (playlistsOrPlaylistSongs == 0) {
            if (playlists.isEmpty()){
                playlistsPanelB.add(Box.createRigidArea(new Dimension(0, 180)));
                playlistsPanelB.add(new JLabel("There are no playlists created for this user!"));
                playlistsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
            }
            else
                this.createTableP();
            playlistsPanelB.add(viewSongsFromPlaylistBtn);
            playlistsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
            playlistsPanelB.add(deletePlaylistBtn);
            playlistsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
            playlistsPanel.add(playlistsPanelB);
            if (playlists.isEmpty()) {
                viewSongsFromPlaylistBtn.setEnabled(false);
                deletePlaylistBtn.setEnabled(false);
            }
            else {
                viewSongsFromPlaylistBtn.setEnabled(true);
                deletePlaylistBtn.setEnabled(true);
            }
        } else if (playlistsOrPlaylistSongs == 1) {
            if (playlistSongs.isEmpty()) {
                playlistsPanelB.add(Box.createRigidArea(new Dimension(0, 120)));
                playlistsPanelB.add(new JLabel("There are no songs in this playlist!"));
                playlistsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
            }
            else
                this.createTablePS();
            playlistsPanelB.add(new JLabel(selectedPlaylistName));
            playlistsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
            playlistsPanelB.add(viewPlaylistsBtn);
            playlistsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
            playlistsPanelB.add(removeSongFromPlaylistBtn);
            playlistsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
            playlistsPanelB.add(playSongBtn);
            playlistsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
            playlistsPanel.add(playlistsPanelB);
            if (playlistSongs.isEmpty()){
                removeSongFromPlaylistBtn.setEnabled(false);
                playSongBtn.setEnabled(false);
            }
            else {
                removeSongFromPlaylistBtn.setEnabled(true);
                playSongBtn.setEnabled(true);
            }
        }
    }

    public void createTableF(){
        friendsTable = generateTable(friends);
        friendsTable.removeColumn(friendsTable.getColumnModel().getColumn(0));
        friendsTable.removeColumn(friendsTable.getColumnModel().getColumn(1));
        friendsTable.removeColumn(friendsTable.getColumnModel().getColumn(1));
        friendsTable.getColumnModel().getColumn(0).setHeaderValue("FRIEND LIST");
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        friendsTable.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        scrollFriends = new JScrollPane(friendsTable);
        scrollFriends.setPreferredSize(new Dimension(400, 500));
        homePanel.add(scrollFriends);
    }

    public void createTableFR(){
        friendRequestsTable = generateTable(friendRequests);
        friendRequestsTable.removeColumn(friendRequestsTable.getColumnModel().getColumn(0));
        friendRequestsTable.removeColumn(friendRequestsTable.getColumnModel().getColumn(1));
        friendRequestsTable.removeColumn(friendRequestsTable.getColumnModel().getColumn(1));
        friendRequestsTable.getColumnModel().getColumn(0).setHeaderValue("FRIEND REQUESTS");
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        friendRequestsTable.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        scrollFriendRequests = new JScrollPane(friendRequestsTable);
        scrollFriendRequests.setPreferredSize(new Dimension(400, 500));
        homePanel.add(scrollFriendRequests);
    }

    public void createTablePFR(){
        pendingFriendRequestsTable = generateTable(pendingFriendRequests);
        pendingFriendRequestsTable.removeColumn(pendingFriendRequestsTable.getColumnModel().getColumn(0));
        pendingFriendRequestsTable.removeColumn(pendingFriendRequestsTable.getColumnModel().getColumn(1));
        pendingFriendRequestsTable.removeColumn(pendingFriendRequestsTable.getColumnModel().getColumn(1));
        pendingFriendRequestsTable.getColumnModel().getColumn(0).setHeaderValue("PENDING FRIEND REQUESTS");
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        pendingFriendRequestsTable.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        scrollPendingFriendRequests = new JScrollPane(pendingFriendRequestsTable);
        scrollPendingFriendRequests.setPreferredSize(new Dimension(400, 500));
        homePanel.add(scrollPendingFriendRequests);
    }

    public void createTableSSR() {
        String[] columnNames = new String[] {"ID", "IDSONG", "TITLE", "ARTIST", "SUGGESTED BY"};
        Object[][] data = new Object[songSuggReceived.size()][5];
        int rowNr = 0;
        for (SongSugg songSugg : songSuggReceived) {
            data[rowNr][0] = songSugg.getId();
            data[rowNr][1] = suggSongsReceived.get(rowNr).getId();
            data[rowNr][2] = suggSongsReceived.get(rowNr).getTitle();
            data[rowNr][3] = suggSongsReceived.get(rowNr).getArtist();
            data[rowNr][4] = whoSuggested.get(rowNr);
            rowNr++;
        }
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        songSuggReceivedTable = new JTable(data, columnNames);
        for (int i = 0; i < columnNames.length; i++)
            songSuggReceivedTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        songSuggReceivedTable.getTableHeader().setBackground(new Color(234, 223, 231));
        songSuggReceivedTable.getTableHeader().setFont(new Font("Helvetica", Font.BOLD, 15));
        songSuggReceivedTable.setFont(new Font("Helvetica", Font.PLAIN, 15));
        songSuggReceivedTable.getTableHeader().setPreferredSize(new Dimension(15, 40));
        songSuggReceivedTable.setRowHeight(30);
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        songSuggReceivedTable.setModel(tableModel);
        scrollSongSuggReceivedTable = new JScrollPane(songSuggReceivedTable);
        scrollSongSuggReceivedTable.setPreferredSize(new Dimension(800, 500));
        songsPanel.add(scrollSongSuggReceivedTable);
    }

    public void createTableS(){
        songsTable = generateTable(songs);
        scrollSongs = new JScrollPane(songsTable);
        scrollSongs.setPreferredSize(new Dimension(800, 500));
        songsPanel.add(scrollSongs);
    }

    public void createTableP(){
        playlistsTable = generateTable(playlists);
        playlistsTable.removeColumn(playlistsTable.getColumnModel().getColumn(1));
        scrollPlaylists = new JScrollPane(playlistsTable);
        scrollPlaylists.setPreferredSize(new Dimension(800, 500));
        playlistsPanel.add(scrollPlaylists);
    }

    public void createTablePS(){
        playlistSongsTable = generateTable(playlistSongs);
        scrollPlaylistSongs = new JScrollPane(playlistSongsTable);
        scrollPlaylistSongs.setPreferredSize(new Dimension(800, 500));
        playlistsPanel.add(scrollPlaylistSongs);
    }

    public static JTable generateTable(List<? extends Object> list) {
        String[] columnNames = new String[list.get(0).getClass().getDeclaredFields().length];
        Object[][] data = new Object[list.size()][list.get(0).getClass().getDeclaredFields().length];
        int nrOfColumns = 0;
        int rowNr = 0;
        int columnNr = 0;
        for (Field field : list.get(0).getClass().getDeclaredFields())
            columnNames[nrOfColumns++] = field.getName().toUpperCase();
        for (Object o : list) {
            for (Field field : o.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    data[rowNr][columnNr++] = field.get(o);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            rowNr++;
            columnNr = 0;
        }
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        JTable table = new JTable(data, columnNames);
        for (int i = 0; i < columnNames.length; i++)
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        table.getTableHeader().setBackground(new Color(234, 223, 231));
        table.getTableHeader().setFont(new Font("Helvetica", Font.BOLD, 15));
        table.setFont(new Font("Helvetica", Font.PLAIN, 15));
        table.getTableHeader().setPreferredSize(new Dimension(15, 40));
        table.setRowHeight(30);
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return !(getColumnName(column).equals("ID") || getColumnName(column).equals("IDUSER") || getColumnName(column).equals("IDPLAYLIST") || getColumnName(column).equals("IDSONG"));
            }
        };
        table.setModel(tableModel);
        return table;
    }

    @Override
    public void setFriends(List<User> friends) {
        if (friends.get(0) == null)
            this.friends.clear();
        else
            this.friends = friends;
    }

    @Override
    public void setFriendRequests(List<User> friendRequests) {
        if (friendRequests.get(0) == null)
            this.friendRequests.clear();
        else
            this.friendRequests = friendRequests;
    }

    @Override
    public void setPendingFriendRequests(List<User> pendingFriendRequests) {
        if (pendingFriendRequests.get(0) == null)
            this.pendingFriendRequests.clear();
        else
            this.pendingFriendRequests = pendingFriendRequests;
    }

    @Override
    public void setSongSuggSent(List<SongSugg> songSuggSent) {
        if(songs.get(0) == null)
            this.songSuggSent.clear();
        else
            this.songSuggSent = songSuggSent;
    }

    @Override
    public void setSongSuggReceived(List<SongSugg> songSuggReceived) {
        if(songSuggReceived.get(0) == null)
            this.songSuggReceived.clear();
        else
            this.songSuggReceived = songSuggReceived;
    }

    @Override
    public void setSuggSongsReceived(List<Song> suggSongsReceived) {
        if(suggSongsReceived.get(0) == null)
            this.suggSongsReceived.clear();
        else
            this.suggSongsReceived = suggSongsReceived;
    }

    @Override
    public void setWhoSuggested(List<String> whoSuggested) {
        this.whoSuggested = whoSuggested;
    }

    @Override
    public void setSongs(List<Song> songs) {
        if(songs.get(0) == null)
            this.songs.clear();
        else
            this.songs = songs;
    }

    @Override
    public void setPlaylists(List<Playlist> playlists) {
        if(playlists.get(0) == null)
            this.playlists.clear();
        else
            this.playlists = playlists;
    }

    @Override
    public void setPlaylistSongs(List<Song> songs) {
        if(songs.get(0) == null)
            this.playlistSongs.clear();
        else
            this.playlistSongs = songs;
    }

    @Override
    public void clearMainPanel() {
        mainPanel.removeAll();
        homePanel.removeAll();
        songsPanel.removeAll();
        playlistsPanel.removeAll();
        mainPanel.revalidate();
        homePanel.revalidate();
        songsPanel.revalidate();
        playlistsPanel.revalidate();
    }

    @Override
    public int getUserId() {
        return user.getId();
    }

    @Override
    public String getUserUsername() {
        return user.getUsername();
    }

    @Override
    public List<Object> addSongSuggOptionPane(List<User> friends) {
        int row = songsTable.getSelectedRow();
        if (songsTable.isRowSelected(songsTable.getSelectedRow())) {
            idNewSongSugg = (int) songsTable.getValueAt(row, 0);
        } else return null;
        Vector<String> selectFriend = new Vector<String>();
        for (User friend : friends) {
            selectFriend.add(friend.getUsername());
        }
        JComboBox selectFromFriends = new JComboBox(selectFriend);
        selectFromFriends.setSelectedIndex(0);
        Object[] message = {"Friend: ", selectFromFriends};
        List<Object> addNewSongSuggOptionPane = new ArrayList<Object>();
        int option = JOptionPane.showConfirmDialog(this, message, "Suggest a song to a friend", JOptionPane.OK_CANCEL_OPTION);
        addNewSongSuggOptionPane.add(option);
        addNewSongSuggOptionPane.add(selectFromFriends.getSelectedItem());
        return addNewSongSuggOptionPane;
    }

    @Override
    public int addFriendsOptionPane() {
        newFriendUsernameTextField = new JTextField(20);
        Object[] message = {"Username: ", newFriendUsernameTextField};
        int option = JOptionPane.showConfirmDialog(this, message, "Add a new friend", JOptionPane.OK_CANCEL_OPTION);
        return option;
    }

    @Override
    public int createNewPlaylistOrAddToExistingOptionPane() {
        Object[] options = {"Create new playlist", "Add to existing playlist"};
        int option = JOptionPane.showOptionDialog(this, "Create new playlist with the suggested song or add to an existing playlist",
                "Accept song suggestion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        return option;
    }

    @Override
    public int liveNotificationOptionPane(Song newSuggSong, String whoSuggested) {
        Object[] options = {"Accept", "Deny"};
        int option = JOptionPane.showOptionDialog(this, newSuggSong.getTitle() + " - " + newSuggSong.getArtist() +
                " --- Suggested by: " + whoSuggested, "New song suggestion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
        return option;
    }

    @Override
    public int[] showSearchByOptionPane() {
        String[] searchBy = {"title", "artist", "album", "genre", "top views"};
        JComboBox criteria = new JComboBox(searchBy);
        criteria.setSelectedIndex(0);
        criteriaTextField = new JTextField(20);
        Object[] message = {"Criteria: ", criteria, criteriaTextField};
        int[] searchByOptionPane = new int[2];
        int option = JOptionPane.showConfirmDialog(this, message, "Search songs by", JOptionPane.OK_CANCEL_OPTION);
        searchByOptionPane[0] = option;
        searchByOptionPane[1] = criteria.getSelectedIndex();
        return searchByOptionPane;
    }

    @Override
    public int createNewPlaylistOptionPane(SongSugg newSongSugg) {
        if (newSongSugg != null) {
            idSongsForNewPlaylist = new ArrayList<>();
            idSongsForNewPlaylist.add(newSongSugg.getIdSong());
            idSongSuggConfirmed = newSongSugg.getId();
        }
        else {
            if (songsOrSongSugg == 0) {
                int[] rows = songsTable.getSelectedRows();
                if (rows.length > 0 && songsTable.getSelectedRows().length > 0) {
                    idSongsForNewPlaylist = new ArrayList<>();
                    for (int row : rows) {
                        idSongsForNewPlaylist.add((int) songsTable.getValueAt(row, 0));
                    }
                }
            } else if (songsOrSongSugg == 1) {
                int row = songSuggReceivedTable.getSelectedRow();
                if (songSuggReceivedTable.isRowSelected(songSuggReceivedTable.getSelectedRow())) {
                    idSongsForNewPlaylist = new ArrayList<>();
                    idSongsForNewPlaylist.add((int) songSuggReceivedTable.getValueAt(row, 1));
                    idSongSuggConfirmed = Integer.parseInt(songSuggReceivedTable.getValueAt(row, 0).toString());
                }
            }
        }
            playlistNameTextField = new JTextField(20);
            Object[] message = {"Playlist name: ", playlistNameTextField};
            int option = JOptionPane.showConfirmDialog(this, message, "Create new playlist", JOptionPane.OK_CANCEL_OPTION);
            return option;
    }

    @Override
    public List<Object> addNewSongsToExistingPlaylistOptionPane(List<Playlist> playlists, SongSugg newSongSugg) {
        if (newSongSugg != null) {
            idSongsForNewPlaylist = new ArrayList<>();
            idSongsForNewPlaylist.add(newSongSugg.getIdSong());
            idSongSuggConfirmed = newSongSugg.getId();
        }
        else {
            if (songsOrSongSugg == 0) {
                int[] rows = songsTable.getSelectedRows();
                if (rows.length > 0 && songsTable.getSelectedRows().length > 0) {
                    idSongsForNewPlaylist = new ArrayList<>();
                    for (int row : rows) {
                        idSongsForNewPlaylist.add((int) songsTable.getValueAt(row, 0));
                    }
                } else return null;
            } else if (songsOrSongSugg == 1) {
                int row = songSuggReceivedTable.getSelectedRow();
                if (songSuggReceivedTable.isRowSelected(songSuggReceivedTable.getSelectedRow())) {
                    idSongsForNewPlaylist = new ArrayList<>();
                    idSongsForNewPlaylist.add((int) songSuggReceivedTable.getValueAt(row, 1));
                    idSongSuggConfirmed = Integer.parseInt(songSuggReceivedTable.getValueAt(row, 0).toString());
                } else return null;
            }
        }
        Vector<String> selectPlaylist = new Vector<String>();
        for (Playlist playlist : playlists) {
            selectPlaylist.add(playlist.getName());
        }
        JComboBox selectFromPlaylist = new JComboBox(selectPlaylist);
        selectFromPlaylist.setSelectedIndex(0);
        Object[] message = {"Playlist: ", selectFromPlaylist};
        List<Object> addNewSongOptionPane = new ArrayList<Object>();
        int option = JOptionPane.showConfirmDialog(this, message, "Add new song to existing playlist", JOptionPane.OK_CANCEL_OPTION);
        addNewSongOptionPane.add(option);
        addNewSongOptionPane.add(selectFromPlaylist.getSelectedItem());
        return addNewSongOptionPane;
    }

    @Override
    public int confirmFriendRequest() {
        int row = friendRequestsTable.getSelectedRow();
        if (friendRequestsTable.isRowSelected(friendRequestsTable.getSelectedRow())) {
            usernameFriendRequestToConfirm = friendRequestsTable.getValueAt(row, 0).toString();
            return 0;
        }
        else
            return -1;
    }

    @Override
    public int denyFriendRequest() {
        int row = friendRequestsTable.getSelectedRow();
        if (friendRequestsTable.isRowSelected(friendRequestsTable.getSelectedRow())) {
            usernameFriendRequestToDeny = friendRequestsTable.getValueAt(row, 0).toString();
            return 0;
        }
        else
            return -1;
    }

    @Override
    public int denySongSuggestion(SongSugg newSongSugg) {
        if (newSongSugg != null) {
            idSongSuggDenied = newSongSugg.getId();
            return 0;
        }
        else {
            int row = songSuggReceivedTable.getSelectedRow();
            if (songSuggReceivedTable.isRowSelected(songSuggReceivedTable.getSelectedRow())) {
                idSongSuggDenied = (int) songSuggReceivedTable.getValueAt(row, 0);
                return 0;
            } else
                return -1;
        }
    }

    @Override
    public int deleteFriend() {
        int row = friendsTable.getSelectedRow();
        if (friendsTable.isRowSelected(friendsTable.getSelectedRow())) {
            usernameFriendToDelete = friendsTable.getValueAt(row, 0).toString();
            return 0;
        }
        else
            return -1;
    }

    @Override
    public int deleteSong() {
        int row = playlistSongsTable.getSelectedRow();
        if (playlistSongsTable.isRowSelected(playlistSongsTable.getSelectedRow())) {
            idSongToDelete = (int) playlistSongsTable.getValueAt(row, 0);
            idPlaylistToDelete = idOfPlaylist;
            return 0;
        }
        else
            return -1;
    }

    @Override
    public int deletePlaylist() {
        int row = playlistsTable.getSelectedRow();
        if (playlistsTable.isRowSelected(playlistsTable.getSelectedRow())) {
            idPlaylistToDelete = (int) playlistsTable.getValueAt(row, 0);
            return 0;
        }
        else
            return -1;
    }

    @Override
    public String getNewFriendUsername() { return newFriendUsernameTextField.getText(); }

    @Override
    public String getCriteria() {
        return criteriaTextField.getText();
    }

    @Override
    public String getPlaylistName() {
        return playlistNameTextField.getText();
    }

    @Override
    public List<Integer> getIdSongsForNewPlaylist() {
        return idSongsForNewPlaylist;
    }

    @Override
    public int playSong() {
        int row = playlistSongsTable.getSelectedRow();
        if (playlistSongsTable.isRowSelected(playlistSongsTable.getSelectedRow())) {
            idToPlay = (int) playlistSongsTable.getValueAt(row, 0);
            return 0;
        }
        else
            return -1;
    }

    @Override
    public void resetIdSongsForNewPlaylist() {
        this.idSongsForNewPlaylist.clear();
    }

    @Override
    public void resetIdNewSongSugg() { idNewSongSugg = -1; }

    @Override
    public void setFriendsOrFriendRequests(int val) { this.friendsOrFriendRequests = val; }

    @Override
    public void setSongsOrSongSugg(int val) { this.songsOrSongSugg = val; }

    @Override
    public void setPlaylistsOrPlaylistsSongs(int val) { this.playlistsOrPlaylistSongs = val; }

    @Override
    public int selectPlaylistToView() {
        int row = playlistsTable.getSelectedRow();
        if (playlistsTable.isRowSelected(playlistsTable.getSelectedRow())) {
            idOfPlaylist = (int) playlistsTable.getValueAt(row, 0);
            selectedPlaylistName = playlistsTable.getValueAt(row, 1).toString();
            return 0;
        }
        else
            return -1;
    }

    @Override
    public int getIdOfPlaylistToView() {
        return idOfPlaylist;
    }

    @Override
    public int getIdSongToDelete() {
        return idSongToDelete;
    }

    @Override
    public int getIdPlaylistToDelete() { return idPlaylistToDelete; }

    @Override
    public int getIdToPlay() { return idToPlay; }

    @Override
    public String getUsernameFriendRequestToConfirm() { return usernameFriendRequestToConfirm; }

    @Override
    public String getUsernameFriendRequestToDeny() { return usernameFriendRequestToDeny; }

    @Override
    public String getUsernameFriendToDelete() { return usernameFriendToDelete; }

    @Override
    public int getNewIdSongSugg() { return idNewSongSugg; }

    @Override
    public int getIdSongSuggConfirmed() { return idSongSuggConfirmed; }

    @Override
    public int getIdSongSuggDenied() { return idSongSuggDenied; }

    @Override
    public void setVisibleRegView(boolean value) { this.setVisible(value); }

    @Override
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
