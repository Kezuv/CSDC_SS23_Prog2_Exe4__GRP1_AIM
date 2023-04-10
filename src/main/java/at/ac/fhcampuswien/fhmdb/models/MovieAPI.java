package at.ac.fhcampuswien.fhmdb.models;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovieAPI {

    private static String buildUrl(String url, String searchField, String searchGenre){
        String finalUrl = url + "?query=" + searchField + "&genre=" + searchGenre;
        return finalUrl;
    }
    public static List<String> movieApiRequest(String searchField, String searchGenre) throws IOException {
        ArrayList<String> returnList = new ArrayList<>();
        String requestUrl = "https://prog2.fh-campuswien.ac.at/movies";
        URL url;
        int responseCode;

        try {
            if (searchField.equals("") && searchGenre.equals("")) {
                url = new URL(requestUrl);
            } else {
                requestUrl = buildUrl(requestUrl, searchField, searchGenre);
                url = new URL(requestUrl);
            }

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", "http.agent");
            conn.setRequestMethod("GET");
            conn.connect();
            responseCode = conn.getResponseCode();

            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {//Not sure about data type
                Scanner responseReader = new Scanner(conn.getInputStream());
                while (responseReader.hasNextLine()) {
                    returnList.add(responseReader.nextLine() + " ");
                }
                return returnList;
            }
        }catch (MalformedURLException e) {
            return null;
        }
    }
}
