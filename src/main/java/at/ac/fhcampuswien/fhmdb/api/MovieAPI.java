package at.ac.fhcampuswien.fhmdb.api;

import java.io.IOException;
import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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
                customURL = customURL + "releasedYear=" + value;
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


    public static String getApiRequest() throws IOException {
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
    public static String getTrueImgUrl(String url, String tagName, String attribute) throws IOException {
        Document metaCode = Jsoup.connect(url).get();
        Element element = metaCode.select(tagName).first();
        assert element != null;
        return element.attr(attribute);
    }
    public static String getImgRequest(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
        return response.body().string();
    }
}
