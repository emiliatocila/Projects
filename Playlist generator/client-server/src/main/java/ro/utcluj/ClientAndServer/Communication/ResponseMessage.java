package ro.utcluj.ClientAndServer.Communication;

import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResponseMessage {
    private String encodedObject;

    public ResponseMessage(String encodedObject) {
        this.encodedObject = encodedObject;
    }

    public String getEncodedObject() {
        return encodedObject;
    }

    public void setEncodedObject(String encodedObject) {
        this.encodedObject = encodedObject;
    }

    public <T extends Object> List<T> getDecodedObject(Class<T> tClass) {
        Gson gson = new Gson();
        List<T> list = new ArrayList<>();
        String[] splitObjs = encodedObject.split("_");
        if (splitObjs.length == 0) {
            return Arrays.asList(gson.fromJson(encodedObject, tClass));
        }
        else {
            for(String sObj : splitObjs) {
                list.add(gson.fromJson(sObj, (Type) tClass));
            }
            return list;
        }
    }

    public void setDecodedObject(Object o) {
        Gson gson = new Gson();
        encodedObject = gson.toJson(o);
    }

}
