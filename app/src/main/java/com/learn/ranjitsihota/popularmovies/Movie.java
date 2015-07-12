package com.learn.ranjitsihota.popularmovies;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Movie implements Serializable {
    private int id;
    private String title;
    private String synopsis;
    private String posterUrl;
    private String releaseDate;
    private String userRating;

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

    public static Movie fromJson(JSONObject jsonObject) {
        Movie movie = new Movie();
        try {
            movie.id = jsonObject.getInt("id");
            movie.title = jsonObject.getString("title");
            movie.synopsis = jsonObject.getString("overview");
            movie.posterUrl = "http://image.tmdb.org/t/p/w500" + jsonObject.getString("poster_path");
            movie.releaseDate = jsonObject.getString("release_date");
            movie.userRating = jsonObject.getString("vote_average");

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
    public static ArrayList<Movie> fromJson(JSONArray jsonArray) {
        ArrayList<Movie> businesses = new ArrayList<Movie>(jsonArray.length());
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
                businesses.add(business);
            }
        }

        return businesses;
    }
}
