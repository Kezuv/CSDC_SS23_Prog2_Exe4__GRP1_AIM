package at.ac.fhcampuswien.fhmdb.api;

import java.io.IOException;
import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class MovieAPI {
    private final static String baseURL = "https://prog2.fh-campuswien.ac.at/movies";
    private static String customSearchParameter = "";
    private static boolean firstParam = true;
    public static String addParam(SearchParameter param, String value){
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

    public static String getApiRequest() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(baseURL+customSearchParameter)
                .header("User-Agent", "http.agent")
                .build();

        Response response = client.newCall(request).execute();
        resetURL();
        return response.body().string();
    }

    public static String getTrueImgUrl(String url) throws IOException {
        Document metaCode = Jsoup.connect(url).get();
        Element element = metaCode.select("meta[property=og:image]").first();
        return element.attr("content");
    }
}
