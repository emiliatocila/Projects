package ro.utcluj.Model.Report;

import ro.utcluj.Model.model.Playlist;
import ro.utcluj.Model.model.Song;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportTXT implements IReport {
    private String username;
    private Map<Playlist, List<Song>> playlistSongs;

    public ReportTXT(Map<Playlist, List<Song>> playlistSongs, String username) {
        this.playlistSongs = playlistSongs;
        this.username = username;
    }

    @Override
    public void generateReport() {
        List<String> lines = new ArrayList<String>();
        lines.add("Playlists for " + username);
        for (Playlist playlist : playlistSongs.keySet()){
            lines.add(playlist.getName());
            int i = 1;
            for (Song song : playlistSongs.get(playlist)) {
                lines.add(i + ". " + song.getTitle() + " - " + song.getArtist() + " - " + song.getAlbum());
                i++;
            }
            lines.add("");
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Generate report");
        int userSelection = fileChooser.showSaveDialog(new JFrame());
        if (userSelection == JFileChooser.APPROVE_OPTION){
            File fileToSave = fileChooser.getSelectedFile();
            File file;
            file = fileToSave;
            if (!fileToSave.toString().substring(fileToSave.toString().length() - 3, fileToSave.toString().length()).equals(".txt"))
                file = new File(fileToSave.getPath() + ".txt");
            try {
                Files.write(file.toPath(), lines, Charset.forName("UTF-8"));
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
