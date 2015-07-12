package com.learn.ranjitsihota.popularmovies;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

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
        //http://private-anon-3bebd182e-themoviedb.apiary-mock.com/3/discover/movie?sort_by=popularity.desc&page=1
        //client.get(url, params, handler);
        client.get("http://private-anon-3bebd182e-themoviedb.apiary-mock.com/3/discover/movie?sort_by=popularity.desc&page=1", params, handler);
    }



    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }
}
