package ro.utcluj.Model.Report;

import ro.utcluj.Model.model.Playlist;
import ro.utcluj.Model.model.Song;
import java.util.List;
import java.util.Map;

public class ReportFactory {

    public ReportFactory(){}
    public IReport getReport(String type, Map<Playlist, List<Song>> playlistSongs, String username) {
        if (type.equals("PDF")) return new ReportPDF(playlistSongs, username);
        else if (type.equals("TXT")) return new ReportTXT(playlistSongs, username);
        else return null;
    }
}
