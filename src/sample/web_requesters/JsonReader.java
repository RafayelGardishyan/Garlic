package sample.web_requesters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sample.data.Const;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JsonReader {

    private static final Logger LOGGER = Logger.getLogger( JsonReader.class.getName() );

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public JsonReader() throws IOException {
        LOGGER.addHandler(new ConsoleHandler());
        LOGGER.addHandler(new FileHandler());
    }

    public static JSONObject GetJsonObject(String url) throws IOException, JSONException {
        URL myurl = new URL(url);
        URLConnection uc = myurl.openConnection();
        String val = (new StringBuffer(Const.server_auth_username).append(":").append(Const.server_auth_password)).toString();
        byte[] base = val.getBytes();
        String authorizationString = "Basic " + new String(Base64.getEncoder().encode(base));
        uc.setRequestProperty ("Authorization", authorizationString);
        try (InputStream is = uc.getInputStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            LOGGER.log(Level.FINE, "Got server json object response");
            return new JSONObject(jsonText);
        }
    }

    public static JSONArray GetJsonArray(String url) throws IOException, JSONException {
        URL myurl = new URL(url);
        URLConnection uc = myurl.openConnection();
        String val = (new StringBuffer(Const.server_auth_username).append(":").append(Const.server_auth_password)).toString();
        byte[] base = val.getBytes();
        String authorizationString = "Basic " + new String(Base64.getEncoder().encode(base));
        uc.setRequestProperty ("Authorization", authorizationString);
        try (InputStream is = uc.getInputStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            LOGGER.log(Level.FINE, "Got server json array response");
            return new JSONArray(jsonText);
        }
    }
}