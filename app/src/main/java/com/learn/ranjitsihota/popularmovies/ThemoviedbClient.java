package com.learn.ranjitsihota.popularmovies;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
public class ThemoviedbClient {
    private final String API_KEY = "52d449f4387ea04a9889e33be3d83813";
    private final String API_BASE_URL = "http://private-anon-3bebd182e-themoviedb.apiary-mock.com/3";
    private AsyncHttpClient client;

    public ThemoviedbClient() {
        this.client = new AsyncHttpClient();
    }


    public void getMovies(JsonHttpResponseHandler handler) {
        String url = getApiUrl("/discover/movie?sort_by=popularity.desc");
        RequestParams params = new RequestParams("apikey", API_KEY);
        client.get(url, params, handler);
    }

    public void getMoviesTwo()
    {

    }

    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }
}
