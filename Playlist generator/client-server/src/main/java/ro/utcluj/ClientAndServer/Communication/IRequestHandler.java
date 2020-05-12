package ro.utcluj.ClientAndServer.Communication;

import java.util.List;

public interface IRequestHandler {
    String encodeRequest(String operation, String params);
    String encodeRequest(String operation, String param, Object o);
    RequestMessage decodeRequest(String encodedMessage);
    String handleRequest(String encodedMessage);
    List<?> decodeResponse(String encodedMessage, Class<?> tclass);
}
