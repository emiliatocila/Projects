package ro.utcluj.Client.View;

import ro.utcluj.Client.Controller.AdminController;
import ro.utcluj.ClientAndServer.Model.Song;
import ro.utcluj.ClientAndServer.Model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.List;

public class AdminView extends JFrame implements IAdminView {

    User user = null;

    List<User> users = null;
    List<Song> songs = null;

    JTabbedPane mainPanel;
    int tabCurrentIndex = 0;

    JPanel regUsersPanel;
    JPanel songsPanel;

    JTable regUsersTable;
    JTable songsTable;
    JScrollPane scrollRegUsers;
    JScrollPane scrollSongs;

    private JButton showAllRegUsersBtn = new JButton("Show all users");
    private JButton insertRegUserBtn = new JButton("Add new user");
    private JButton deleteRegUserBtn = new JButton("Delete user");
    private JButton updateRegUserBtn = new JButton("Update user");
    private JButton generateReportRegUserBtn = new JButton("Generate report");

    private JButton showAllSongsBtn = new JButton("Show all songs");
    private JButton insertSongBtn = new JButton("Add new song");
    private JButton deleteSongBtn = new JButton("Delete song");
    private JButton updateSongBtn = new JButton("Update song");
    private JButton logOutBtn = new JButton("Log out");

    JTextField usernameTextField;
    JTextField passwordTextField;
    JTextField titleTextField;
    JTextField artistTextField;
    JTextField albumTextField;
    JTextField genreTextField;
    JTextField viewCountTextField;

    private int idToDelete = -1;
    private int idToUpdate = -1;
    private int idToGenerate = -1;
    private String usernameToGenerate = null;
    private String reportType = null;
    private String usernameToUpdate = null;
    private String titleToUpdate = null;
    private String artistToUpdate = null;
    private String albumToUpdate = null;
    private String genreToUpdate = null;
    private int viewCountToUpdate = 0;
    private double ratingToUpdate = -1.0;

    public AdminView(User user){
        this.user = user;
        mainPanel = new JTabbedPane();
        this.setContentPane(mainPanel);
        this.pack();
        this.setSize(new Dimension(1100, 600));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Logged in as administrator - " + user.getUsername());
        AdminController adminController = new AdminController(this);
        showAllRegUsersBtn.addActionListener(e -> adminController.showAllRegUsers());
        insertRegUserBtn.addActionListener(e -> adminController.insertRegUser());
        deleteRegUserBtn.addActionListener(e -> adminController.deleteRegUser());
        updateRegUserBtn.addActionListener(e -> adminController.updateRegUser());
        generateReportRegUserBtn.addActionListener(e -> adminController.generateReportRegUser());
        showAllSongsBtn.addActionListener(e -> adminController.showAllSongs());
        insertSongBtn.addActionListener(e -> adminController.insertSong());
        deleteSongBtn.addActionListener(e -> adminController.deleteSong());
        updateSongBtn.addActionListener(e -> adminController.updateSong());
        logOutBtn.addActionListener(e -> adminController.logOut());
    }

    @Override
    public void init(){
        initRegUsers();
        initSongs();
        mainPanel.addTab("Regular Users", regUsersPanel);
        mainPanel.addTab("Songs", songsPanel);
        mainPanel.setSelectedIndex(tabCurrentIndex);
    }

    public void initRegUsers(){
        regUsersPanel = new JPanel();
        regUsersPanel.setLayout(new FlowLayout());
        if (users.isEmpty())
            regUsersPanel.add(new JLabel("There are no users!"));
        else
            this.createTableU();
        JPanel regUsersPanelB = new JPanel();
        regUsersPanelB.setLayout(new BoxLayout(regUsersPanelB, BoxLayout.Y_AXIS));
        regUsersPanelB.add(logOutBtn);
        regUsersPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
        //regUsersPanelB.add(showAllRegUsersBtn);
        //regUsersPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
        regUsersPanelB.add(insertRegUserBtn);
        regUsersPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
        regUsersPanelB.add(deleteRegUserBtn);
        regUsersPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
        regUsersPanelB.add(updateRegUserBtn);
        regUsersPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
        regUsersPanelB.add(generateReportRegUserBtn);
        regUsersPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
        regUsersPanel.add(regUsersPanelB);
        if (users.isEmpty()){
            deleteRegUserBtn.setEnabled(false);
            updateRegUserBtn.setEnabled(false);
            generateReportRegUserBtn.setEnabled(false);
        }
        else {
            deleteRegUserBtn.setEnabled(true);
            updateRegUserBtn.setEnabled(true);
            generateReportRegUserBtn.setEnabled(true);
        }
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
        songsPanelB.add(showAllSongsBtn);
        songsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
        songsPanelB.add(insertSongBtn);
        songsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
        songsPanelB.add(deleteSongBtn);
        songsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
        songsPanelB.add(updateSongBtn);
        songsPanelB.add(Box.createRigidArea(new Dimension(0, 20)));
        songsPanel.add(songsPanelB);
        if (songs.isEmpty()){
            deleteSongBtn.setEnabled(false);
            updateSongBtn.setEnabled(false);
        }
        else {
            deleteSongBtn.setEnabled(true);
            updateSongBtn.setEnabled(true);
        }
    }

    public void createTableU(){
        regUsersTable = generateTable(users);
        regUsersTable.removeColumn(regUsersTable.getColumnModel().getColumn(2));
        regUsersTable.removeColumn(regUsersTable.getColumnModel().getColumn(2));
        scrollRegUsers = new JScrollPane(regUsersTable);
        scrollRegUsers.setPreferredSize(new Dimension(800, 500));
        regUsersPanel.add(scrollRegUsers);
    }

    public void createTableS(){
        songsTable = generateTable(songs);
        scrollSongs = new JScrollPane(songsTable);
        scrollSongs.setPreferredSize(new Dimension(800, 500));
        songsPanel.add(scrollSongs);
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
                return !(getColumnName(column).equals("ID") || getColumnName(column).equals("IDUSER") || getColumnName(column).equals("IDPLAYLIST"));
            }
        };
        table.setModel(tableModel);
        return table;
    }

    @Override
    public void setUsers(List<User> users){
        if(users.get(0) == null)
            this.users.clear();
        else
            this.users = users;
    }

    @Override
    public void setSongs(List<Song> songs) {
        if(songs.get(0) == null)
            this.songs.clear();
        else
            this.songs = songs;
    }

    @Override
    public void clearMainPanel(){
        tabCurrentIndex = mainPanel.getSelectedIndex();
        mainPanel.removeAll();
        regUsersPanel.removeAll();
        songsPanel.removeAll();
        mainPanel.revalidate();
        regUsersPanel.revalidate();
        songsPanel.revalidate();
    }

    @Override
    public int showInsertUserOptionPane() {
        usernameTextField = new JTextField(20);
        passwordTextField = new JTextField(20);
        Object[] message = {"Username: ", usernameTextField, "Password: ", passwordTextField};
        int option = JOptionPane.showConfirmDialog(this, message, "Add a new user", JOptionPane.OK_CANCEL_OPTION);
        return option;
    }

    @Override
    public int showInsertSongOptionPane() {
        titleTextField = new JTextField(20);
        artistTextField = new JTextField(20);
        albumTextField = new JTextField(20);
        genreTextField = new JTextField(20);
        Object[] message = {"Title: ", titleTextField, "Artist: ", artistTextField, "Album", albumTextField, "Genre: ", genreTextField};
        int option = JOptionPane.showConfirmDialog(this, message, "Add a new song", JOptionPane.OK_CANCEL_OPTION);
        return option;
    }

    @Override
    public int deleteUser() {
        int row = regUsersTable.getSelectedRow();
        if (regUsersTable.isRowSelected(regUsersTable.getSelectedRow())) {
            idToDelete = (int) regUsersTable.getValueAt(row, 0);
            return 0;
        }
        else
            return -1;
    }

    @Override
    public int deleteSong() {
        int row = songsTable.getSelectedRow();
        if (songsTable.isRowSelected(songsTable.getSelectedRow())) {
            idToDelete = (int) songsTable.getValueAt(row, 0);
            return 0;
        }
        else
            return -1;
    }

    @Override
    public int updateUser() {
        int row = regUsersTable.getSelectedRow();
        if (regUsersTable.isRowSelected(regUsersTable.getSelectedRow())){
            if (regUsersTable.isEditing())
                regUsersTable.getCellEditor().stopCellEditing();
            idToUpdate = (int) regUsersTable.getValueAt(row, 0);
            usernameToUpdate = regUsersTable.getValueAt(row, 1).toString();
            return 0;
        } else return -1;
    }

    @Override
    public int updateSong() {
        int row = songsTable.getSelectedRow();
        if (songsTable.isRowSelected(songsTable.getSelectedRow())){
            if (songsTable.isEditing())
                songsTable.getCellEditor().stopCellEditing();
            idToUpdate = (int) songsTable.getValueAt(row, 0);
            titleToUpdate = songsTable.getValueAt(row, 1).toString();
            artistToUpdate = songsTable.getValueAt(row, 2).toString();
            albumToUpdate = songsTable.getValueAt(row, 3).toString();
            genreToUpdate = songsTable.getValueAt(row, 4).toString();
            viewCountToUpdate = Integer.parseInt(songsTable.getValueAt(row, 5).toString());
            ratingToUpdate = Double.parseDouble(songsTable.getValueAt(row, 6).toString());
            return 0;
        } else return -1;
    }

    @Override
    public void setVisibleAdminView(boolean value) { this.setVisible(value); }

    @Override
    public String getUsername() {
        return usernameTextField.getText();
    }

    @Override
    public String getPassword() {
        return passwordTextField.getText();
    }

    @Override
    public int generateReport() {
        String[] reportType = {"txt", "pdf"};
        JComboBox type = new JComboBox(reportType);
        type.setSelectedIndex(0);
        Object[] message = {"Report type: ", type};
        int row = regUsersTable.getSelectedRow();
        if (regUsersTable.isRowSelected(regUsersTable.getSelectedRow())) {
            idToGenerate = (int) regUsersTable.getValueAt(row, 0);
            usernameToGenerate = regUsersTable.getValueAt(row, 1).toString();
            int option = JOptionPane.showConfirmDialog(this, message, "Generate report", JOptionPane.OK_CANCEL_OPTION);
            if (type.getSelectedItem().toString().equals("txt"))
                this.reportType = "TXT";
            else
                this.reportType = "PDF";
            return option;
        }
        else
            return -1;
    }

    @Override
    public String getTitleSong() {
        return titleTextField.getText();
    }

    @Override
    public String getArtist() {
        return artistTextField.getText();
    }

    @Override
    public String getAlbum() {
        return albumTextField.getText();
    }

    @Override
    public String getGenre() {
        return genreTextField.getText();
    }

    @Override
    public int getIdToDelete() {
        return idToDelete;
    }

    @Override
    public int getIdToUpdate() {
        return idToUpdate;
    }

    @Override
    public int getIdToGenerate() { return idToGenerate; }

    @Override
    public String getTypeOfReport() { return reportType; }

    @Override
    public String getUsernameToGenerate() { return usernameToGenerate; }

    @Override
    public String getUsernameToUpdate() {
        return usernameToUpdate;
    }

    @Override
    public String getTitleToUpdate() {
        return titleToUpdate;
    }

    @Override
    public String getArtistToUpdate() {
        return artistToUpdate;
    }

    @Override
    public String getAlbumToUpdate() {
        return albumToUpdate;
    }

    @Override
    public String getGenreToUpdate() {
        return genreToUpdate;
    }

    @Override
    public int getViewCountToUpdate() {
        return viewCountToUpdate;
    }

    @Override
    public double getRatingToUpdate() { return ratingToUpdate; }

    @Override
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
