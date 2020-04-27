package ro.utcluj.Model.Report;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import ro.utcluj.Model.model.Playlist;
import ro.utcluj.Model.model.Song;
import com.itextpdf.text.Document;
import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportPDF implements IReport {
    private String username;
    private Map<Playlist, List<Song>> playlistSongs;

    public ReportPDF(Map<Playlist, List<Song>> playlistSongs, String username) {
        this.playlistSongs = playlistSongs;
        this.username = username;
    }

    @Override
    public void generateReport() {
        Document document = new Document();
        Paragraph paragraph = new Paragraph();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Generate report");
        int userSelection = fileChooser.showSaveDialog(new JFrame());
        if (userSelection == JFileChooser.APPROVE_OPTION){
            File fileToSave = fileChooser.getSelectedFile();
            File file;
            file = fileToSave;
            if (!fileToSave.toString().substring(fileToSave.toString().length() - 3, fileToSave.toString().length()).equals(".pdf"))
                file = new File(fileToSave.getPath() + ".pdf");
            try {
                PdfWriter.getInstance(document, new FileOutputStream(file.getPath()));
                document.open();
                paragraph.add("Playlists for " + username);
                document.add(paragraph);
                for (Playlist playlist : playlistSongs.keySet()){
                    paragraph.clear();
                    paragraph.add(playlist.getName());
                    document.add(paragraph);
                    int i = 1;
                    for (Song song : playlistSongs.get(playlist)) {
                        paragraph.clear();
                        paragraph.add(i + ". " + song.getTitle() + " - " + song.getArtist() + " - " + song.getAlbum());
                        document.add(paragraph);
                        i++;
                    }
                    paragraph.clear();
                    paragraph.add("");
                    document.add(paragraph);
                }
                document.close();
            } catch(IOException | DocumentException ex) {
                ex.printStackTrace();
            }
        }
    }
}
