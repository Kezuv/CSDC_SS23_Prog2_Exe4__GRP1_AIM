package at.ac.fhcampuswien.fhmdb.api;

import java.io.IOException;

import okhttp3.*;

public class MovieAPI {

    private static String customURL = "https://prog2.fh-campuswien.ac.at/movies";
    private static boolean firstParam = true;
    public static String addParam(SearchParameter param, String value){
        if (firstParam){
            customURL = customURL + "?";
        } else {
            customURL = customURL + "&";
        }
        firstParam = false;

        switch (param){
            case CUSTOMSEARCH: ;
                customURL = customURL + "query=" + value;
                break;
            case GENRE:
                customURL = customURL + "genre=" + value;
                break;
            case YEAR:
                customURL = customURL + "releaseYear=" + value;
                break;
            case RATING:
                customURL = customURL + "ratingFrom=" + value;
                break;
        }
        return customURL;
    }

    public static void resetURL(){
        customURL = "https://prog2.fh-campuswien.ac.at/movies";
        firstParam = true;
    }


    public static String getRequest() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(customURL)
                .header("User-Agent", "http.agent")
                .build();

        Response response = client.newCall(request).execute();
        resetURL();
        return response.body().string();
    }

    public static String getCustomURL() {
        return customURL;
    }
}
