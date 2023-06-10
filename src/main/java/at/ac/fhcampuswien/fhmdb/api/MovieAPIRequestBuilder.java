package at.ac.fhcampuswien.fhmdb.api;

public class MovieAPIRequestBuilder {
    private String baseURL = null;  // set the base URL here
    private String customSearchParameter = "";
    private boolean firstParam = true;

    public MovieAPIRequestBuilder() {
        // here set your base URL
        this.baseURL = "https://prog2.fh-campuswien.ac.at/movies";
    }

    public MovieAPIRequestBuilder query(String value) {
        addParam(SearchParameter.CUSTOMSEARCH, value);
        return this;
    }

    public MovieAPIRequestBuilder genre(String value) {
        addParam(SearchParameter.GENRE, value);
        return this;
    }

    public MovieAPIRequestBuilder releaseYear(String value) {
        addParam(SearchParameter.YEAR, value);
        return this;
    }

    public MovieAPIRequestBuilder ratingFrom(String value) {
        addParam(SearchParameter.RATING, value);
        return this;
    }

    public String build() {
        String finalURL = baseURL + customSearchParameter;
        resetURL();
        return finalURL;
    }

    private void addParam(SearchParameter param, String value) {
        if (firstParam){
            customSearchParameter += "?";
        } else {
            customSearchParameter += "&";
        }
        firstParam = false;
        customSearchParameter += param.toString().toLowerCase() + "=" + value;
    }

    private void resetURL(){
        customSearchParameter = "";
        firstParam = true;
    }
}
