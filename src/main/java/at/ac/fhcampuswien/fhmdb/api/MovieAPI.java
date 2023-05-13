package at.ac.fhcampuswien.fhmdb.api;

import java.io.IOException;

import at.ac.fhcampuswien.fhmdb.Exceptions.MovieApiException;
import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class MovieAPI {
    private final static String baseURL = "https://prog2.fh-campuswien.ac.at/movies";
    private static String customSearchParameter = "";
    private static boolean firstParam = true;

    public static String addParam(SearchParameter param, String value) throws MovieApiException {
        if (param == null) {
            throw new MovieApiException("Invalid SearchParameter input: parameter cannot be null.");
        }
        if (value == null || value.isEmpty()) {
            throw new MovieApiException("Invalid SearchParameter value: value cannot be null or empty.");
        }
        if (firstParam){
            customSearchParameter += "?";
        } else {
            customSearchParameter += "&";
        }
        firstParam = false;

        switch (param) {
            case CUSTOMSEARCH -> customSearchParameter += "query=" + value;
            case GENRE -> customSearchParameter += "genre=" + value;
            case YEAR -> customSearchParameter += "releaseYear=" + value;
            case RATING -> customSearchParameter += "ratingFrom=" + value;
        }
        return customSearchParameter;
    }

    public static void resetURL(){
        customSearchParameter = "";
        firstParam = true;
    }

    public static String getApiRequest() throws MovieApiException {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(baseURL+customSearchParameter)
                    .header("User-Agent", "http.agent")
                    .build();

            Response response = client.newCall(request).execute();
            resetURL();
            return response.body().string();
        } catch (IOException e) {
            throw new MovieApiException(MovieApiException.handleException(e));
        }
    }

    public static String getTrueImgUrl(String url) throws MovieApiException {
        if (url == null || url.isEmpty()) {
            throw new MovieApiException("Invalid URL input: URL cannot be null or empty.");
        }

        try {
            Document metaCode = Jsoup.connect(url).get();
            Element element = metaCode.select("meta[property=og:image]").first();
            if (element == null) {
                throw new MovieApiException("Cannot find meta tag for image in the given URL.");
            }
            return element.attr("content");
        } catch (IOException e) {
            throw new MovieApiException("Error retrieving meta tag from the given URL: " + e.getMessage());
        }
    }

}
