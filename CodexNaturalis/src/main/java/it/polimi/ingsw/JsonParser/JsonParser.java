package it.polimi.ingsw.JsonParser;


import java.io.InputStream;

public class JsonParser {
    public static InputStream getStreamFromPath(String path){
        System.out.println(path);
        InputStream inputStream = JsonParser.class.getResourceAsStream(path);
        return inputStream;
    }
}
