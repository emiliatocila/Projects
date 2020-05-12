package ro.utcluj.ClientAndServer.Communication;

import java.util.HashMap;
import java.util.Map;

public class RequestMessage {
    private String operation;
    private String params;
    private Map<String, String> paramValues;

    public RequestMessage() {
        this.paramValues = new HashMap<String, String>();
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Map<String, String> getParamValues() {
        return paramValues;
    }

    public void setParamValues(Map<String, String> paramValues) {
        this.paramValues = paramValues;
    }
}
