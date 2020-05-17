package ro.utcluj.ClientAndServer.Communication;

import java.util.List;

public interface IRequestHandler {
    String encodeRequest(String operation, String params);
    String encodeRequest(String operation, String param, Object o);
    RequestMessage decodeRequest(String encodedMessage);
    String handleRequest(String encodedMessage);
    String sendRequestToServer(String encodedRequest);
    <T extends Object> List<T> decodeResponse(String encodedMessage, Class<T> tclass);
    <T extends Object> List<T> getResult(String operation, String params, Class<T> tclass);
    <T extends Object> List<T> getResult(String operation, String params, Object o, Class<T> tclass);
}
