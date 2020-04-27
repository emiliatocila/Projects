package ro.utcluj.View;

import ro.utcluj.Controller.RegUserController;
import ro.utcluj.Main;
import ro.utcluj.Model.model.Playlist;
import ro.utcluj.Model.model.Song;
import ro.utcluj.Model.model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class RegularView extends JFrame implements IRegularView{

    User user = null;
    List<Song> songs = new ArrayList<Song>();
    List<Playlist> playlists = new ArrayList<Playlist>();
    List<Song> playlistSongs = new ArrayList<Song>();

    JTabbedPane mainPanel;

    JPanel songsPanel;
    JPanel playlistsPanel;

    JTable songsTable;
    JTable playlistsTable;
    JTable playlistSongsTable;
    JScrollPane scrollSongs;
    JScrollPane scrollPlaylists;
    JScrollPane scrollPlaylistSongs;

    private JButton showAllSongsBtn = new JButton("Show all songs");
    private JButton searchBySongsBtn = new JButton("Search by criteria");
    private JButton createNewPlaylistBtn = new JButton("Create new playlist");
    private JButton addSongToAnExistingPlaylistBtn = new JButton("Add song(s) to an existing playlist");
    private JButton viewSongsFromPlaylistBtn = new JButton("View playlist");
    private JButton viewPlaylistsBtn = new JButton("View all playlists");
    private JButton removeSongFromPlaylistBtn = new JButton("Remove song from playlist");
    private JButton playSongBtn = new JButton("Play song");
    private JButton logOutBtn = new JButton("Log out");

    JTextField criteriaTextField;
    JTextField playlistNameTextField;

    private int playlistsOrPlaylistSongs = 0;
    private String selectedPlaylistName = null;
    private int idOfPlaylist = -1;
    private int idSongToDelete = -1;
    private int idPlaylistToDelete = -1;
    private List<Integer> idSongsForNewPlaylist;
    private int idToPlay = -1;

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
        showAllSongsBtn.addActionListener(e -> regUserController.showAllSongs());
        searchBySongsBtn.addActionListener(e -> regUserController.searchBySongs());
        createNewPlaylistBtn.addActionListener(e -> regUserController.createNewPlaylist());
        addSongToAnExistingPlaylistBtn.addActionListener(e -> regUserController.addSongsToAnExistingPlaylist());
        viewSongsFromPlaylistBtn.addActionListener(e -> regUserController.viewPlaylist());
        viewPlaylistsBtn.addActionListener(e -> regUserController.viewAllPlaylists());
        removeSongFromPlaylistBtn.addActionListener(e -> regUserController.removeSongFromPlaylist());
        playSongBtn.addActionListener(e -> regUserController.playSong());
        logOutBtn.addActionListener(e -> this.setVisible(false));
        logOutBtn.addActionListener(e -> Main.main(null));
    }

    @Override
    public void init() {
        initSongs();
        initPlaylists();
        mainPanel.addTab("Songs", songsPanel);
        mainPanel.addTab("Playlists", playlistsPanel);
    }

    public void initSongs(){
        songsPanel = new JPanel();
        songsPanel.setLayout(new FlowLayout());
        if (songs.isEmpty())
            songsPanel.add(new JLabel("There are no songs!"));
        else
            this.createTableS();
        JPanel songsPanelB = new JPanel();
        songsPanelB.setLayout(new BoxLayout(songsPanelB, BoxLayout.Y_AXIS));
        songsPanelB.add(logOutBtn);
        songsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
        songsPanelB.add(showAllSongsBtn);
        songsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
        songsPanelB.add(searchBySongsBtn);
        songsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
        songsPanelB.add(createNewPlaylistBtn);
        songsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
        songsPanelB.add(addSongToAnExistingPlaylistBtn);
        songsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
        songsPanel.add(songsPanelB);
        if (songs.isEmpty()){
            searchBySongsBtn.setEnabled(false);
            createNewPlaylistBtn.setEnabled(false);
            addSongToAnExistingPlaylistBtn.setEnabled(false);
        }
        else {
            searchBySongsBtn.setEnabled(true);
            createNewPlaylistBtn.setEnabled(true);
            addSongToAnExistingPlaylistBtn.setEnabled(true);
        }
    }

    public void initPlaylists(){
        playlistsPanel = new JPanel();
        playlistsPanel.setLayout(new FlowLayout());
        if (playlists.isEmpty()){
            playlistsPanel.add(new JLabel("There are no playlists created for this user!"));
        }
        else {
            if (playlistsOrPlaylistSongs == 0) {
                this.createTableP();
                JPanel playlistsPanelB = new JPanel();
                playlistsPanelB.setLayout(new BoxLayout(playlistsPanelB, BoxLayout.Y_AXIS));
                playlistsPanelB.add(viewSongsFromPlaylistBtn);
                playlistsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
                playlistsPanel.add(playlistsPanelB);
            } else if (playlistsOrPlaylistSongs == 1) {
                if (playlistSongs.isEmpty())
                    playlistsPanel.add(new JLabel("There are no songs in this playlist!"));
                else
                    this.createTablePS();
                JPanel playlistsPanelB = new JPanel();
                playlistsPanelB.setLayout(new BoxLayout(playlistsPanelB, BoxLayout.Y_AXIS));
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
    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    @Override
    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    @Override
    public void setPlaylistSongs(List<Song> songs) {
        this.playlistSongs = songs;
    }

    @Override
    public void clearMainPanel() {
        mainPanel.removeAll();
        songsPanel.removeAll();
        playlistsPanel.removeAll();
        mainPanel.revalidate();
        songsPanel.revalidate();
        playlistsPanel.revalidate();
    }

    @Override
    public int getUserId() {
        return user.getId();
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
    public int createNewPlaylistOptionPane() {
        int[] rows = songsTable.getSelectedRows();
        if (rows.length > 0 && songsTable.getSelectedRows().length > 0) {
            idSongsForNewPlaylist = new ArrayList<>();
            for (int row : rows) {
                idSongsForNewPlaylist.add((int) songsTable.getValueAt(row, 0));
            }
        }
        playlistNameTextField = new JTextField(20);
        Object[] message = {"Playlist name: ", playlistNameTextField};
        int option = JOptionPane.showConfirmDialog(this, message, "Create new playlist", JOptionPane.OK_CANCEL_OPTION);
        return option;
    }

    @Override
    public List<Object> addNewSongsToExistingPlaylistOptionPane(List<Playlist> playlists) {
        int[] rows = songsTable.getSelectedRows();
        if (rows.length > 0 && songsTable.getSelectedRows().length > 0) {
            idSongsForNewPlaylist = new ArrayList<>();
            for (int row : rows) {
                idSongsForNewPlaylist.add((int) songsTable.getValueAt(row, 0));
            }
        } else return null;
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
    public void setPlaylistsOrPlaylistsSongs(int val) {
        this.playlistsOrPlaylistSongs = val;
    }

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
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
