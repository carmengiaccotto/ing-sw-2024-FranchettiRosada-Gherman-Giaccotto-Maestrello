package it.polimi.ingsw.JsonParser;

import java.io.InputStream;

/**
 * This class is used to parse JSON files in the application.
 * It provides a method to get an InputStream from a given path.
 */
public class JsonParser {

    /**
     * This method is used to get an InputStream from a given path.
     * It prints the path to the console and then retrieves the InputStream associated with the path.
     * The path is relative to the location of the JsonParser class.
     *
     * @param path The path to the resource.
     * @return InputStream The InputStream associated with the given path.
     */
    public static InputStream getStreamFromPath(String path){
        InputStream inputStream = JsonParser.class.getResourceAsStream(path);
        return inputStream;
    }
}