package at.ac.fhcampuswien.fhmdb.api;

import java.io.IOException;

import okhttp3.*;

public class MovieAPI {
    private static String buildUrl(String url, String searchField, String searchGenre){
        String finalUrl = url + "?query=" + searchField + "&genre=" + searchGenre;
        return finalUrl;
    }
    public static String movieApiRequest(String searchField, String genre) throws IOException {
  //      String requestUrl = "http://localhost:8080/movies";
        String requestUrl = "https://prog2.fh-campuswien.ac.at/movies";
        if (searchField.equals("") && genre == null) {
                return getRequest(requestUrl);
        } else {
                requestUrl = buildUrl(requestUrl, searchField, genre);
                return getRequest(requestUrl);
        }
    }

    public static String getRequest(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "http.agent")
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
