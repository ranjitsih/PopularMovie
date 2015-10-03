package com.learn.ranjitsihota.popularmovies;


import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Movie implements Serializable {
    private int id;
    private String title;
    private String synopsis;
    private String posterUrl;
    private String releaseDate;
    private String userRating;
    private String trailerUrl;
    private boolean isFavorite;

    public String getTitle() {return title;}
    public int getId() {return id;}
    public String getSynopsis() {
        return synopsis;
    }
    public String getPosterUrl() {
        return posterUrl;
    }
    public String getReleaseDate() {
        return releaseDate;
    }
    public String getUserRating() {
        return userRating;
    }
    public String getTrailerUrl() {return trailerUrl;}
    public boolean getIsFavorite() {return isFavorite;}
    public void  setIsFavorite(boolean value)
    {
        isFavorite = value;

    }


    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    public static Movie fromJson(JSONObject jsonObject) {
        Movie movie = new Movie();
        try {

            movie.id = jsonObject.getInt("id");
            movie.title = jsonObject.getString("title");
            movie.synopsis = jsonObject.getString("overview");
            movie.posterUrl = "http://image.tmdb.org/t/p/w500" + jsonObject.getString("poster_path");
            movie.releaseDate = jsonObject.getString("release_date");
            movie.userRating = jsonObject.getString("vote_average");

            //movie.trailerUrl = GetTrailerUrl("http://api.themoviedb.org/3/movie/" + movie.id +"/videos?api_key=7e53348ae448d88502f968a61ae1b9ee");

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return movie;
    }
    public static Movie from() {
        Movie movie = new Movie();
        movie.id = 233;
        movie.title = "Fight Club";
        movie.synopsis = "abc abc abc ";
        movie.posterUrl = "http://image.tmdb.org/t/p/w500/8uO0gUM8aNqYLs1OsTBQiXu0fEv.jpg";
        movie.releaseDate = "12-33-33";
        movie.userRating = "7.99";
        return movie;
    }

    public static Movie from2() {
        Movie movie = new Movie();
        movie.id = 233;
        movie.title = "Jurassic World";
        movie.synopsis = "abc abc abc ";
        movie.posterUrl = "http://image.tmdb.org/t/p/w500/uXZYawqUsChGSj54wcuBtEdUJbh.jpg";
        movie.releaseDate = "12-33-33";
        movie.userRating = "7.99";
        return movie;
    }
    public static ArrayList<Movie> fromJson(JSONArray jsonArray,ArrayList<Favorite> favorities,boolean favoritesSortOn) {
        ArrayList<Movie> businesses = new ArrayList<Movie>(jsonArray.length());
        int  length = favorities.size();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject businessJson = null;
            try {
                businessJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Movie business = Movie.fromJson(businessJson);
            if (business != null) {

                for(int j = 0; j < length;j++)
                {
                    if(favorities.get(j).getID() == business.getId())
                    {
                        business.isFavorite = true;
                        break;
                    }
                    else
                    {
                        business.isFavorite = false;
                    }
                }
                if(favoritesSortOn)
                {
                    if(business.isFavorite) {
                        businesses.add(business);
                    }
                }
                else {
                    businesses.add(business);
                }
            }
        }

        return businesses;
    }

}
