package ro.utcluj.ClientAndServer.Communication;

import com.google.gson.Gson;
import ro.utcluj.ClientAndServer.Model.SongSugg;
import java.util.*;

public class LiveNotificationHandler extends Observable implements Observer {

    public LiveNotificationHandler(){
    }

    public String handleLiveNotification(SongSugg songSugg) {
        Gson gson = new Gson();
        String responseMessage = "";
        responseMessage += gson.toJson(songSugg);
        return responseMessage;
    }


    @Override
    public void update(Observable o, Object arg) {
        SongSugg songSugg = ((SongSugg)arg);
        String responseMessage = "LIVENOTIFICATION_";
        responseMessage += this.handleLiveNotification(songSugg);
        responseMessage += "_";
        setChanged();
        notifyObservers(responseMessage);
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

}
