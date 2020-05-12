package ro.utcluj.ClientAndServer.Communication;

import com.google.gson.Gson;
import ro.utcluj.ClientAndServer.Model.SongSugg;
import java.util.Observable;

public class LiveNotificationResponse extends Observable {
    public LiveNotificationResponse(){
    }

    public void handle(String encodedResponse) {
        SongSugg newSongSugg = this.getDecodedObject(encodedResponse);
        setChanged();
        notifyObservers(newSongSugg);
    }

    public SongSugg getDecodedObject(String encodedObject) {
        Gson gson = new Gson();
        String[] splitObjs = encodedObject.split("_");
        return gson.fromJson(splitObjs[1], SongSugg.class);
    }

}
