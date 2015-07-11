package com.learn.ranjitsihota.popularmovies;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.loopj.android.http.JsonHttpResponseHandler;


public class MainActivity extends  Activity {
    private ListView lvMovies;
    private MoviesAdapter adapterMovies;
    private ThemoviedbClient client;
    public static final String MOVIE_DETAIL_KEY = "movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvMovies = (ListView) findViewById(R.id.lvMovies);
        ArrayList<Movie> aMovies = new ArrayList<Movie>();
        adapterMovies = new MoviesAdapter(this, aMovies);
        lvMovies.setAdapter(adapterMovies);
        // Fetch the data remotely
        fetchBoxOfficeMovies();
        setupMovieSelectedListener();
    }

    private void fetchBoxOfficeMovies() {
        Movie movie =  Movie.from();
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(movie);
        movies.add(movie);
        adapterMovies.addAll(movies);

//        client = new ThemoviedbClient();
//        client.getMovies(new JsonHttpResponseHandler() {
//            public void onSuccess(int code, JSONObject body) {
//                JSONArray items = null;
//                try {
//                    // Get the movies json array
//                    items = body.getJSONArray("movies");
//                    // Parse json array into array of model objects
//                    ArrayList<Movie> movies = Movie.fromJson(items);
//                    // Load model objects into the adapter which displays them
//                    adapterMovies.addAll(movies);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }


    public void setupMovieSelectedListener() {
        lvMovies.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position, long rowId) {
                Intent i = new Intent(MainActivity.this, DetailActivity.class);
                //i.putExtra(MOVIE_DETAIL_KEY, adapterMovies.getItem(position));
                i.putExtra(MOVIE_DETAIL_KEY,adapterMovies.getItem(position).getId());
                startActivity(i);
            }
        });
    }

}
