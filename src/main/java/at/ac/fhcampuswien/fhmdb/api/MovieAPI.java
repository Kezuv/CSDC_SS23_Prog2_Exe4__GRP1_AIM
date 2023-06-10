package at.ac.fhcampuswien.fhmdb.api;

import at.ac.fhcampuswien.fhmdb.Exceptions.MovieApiException;
import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;

public class MovieAPI {

    public static String getApiRequest(MovieAPIRequestBuilder builder) throws MovieApiException {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(builder.build())
                    .header("User-Agent", "http.agent")
                    .build();

            Response response = client.newCall(request).execute();
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
