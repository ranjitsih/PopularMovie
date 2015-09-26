package com.learn.ranjitsihota.popularmovies;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.net.Uri;

import com.squareup.picasso.Picasso;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import android.widget.ListView;
import android.widget.ArrayAdapter;

public class DetailActivity extends Activity {
    private ImageView ivPosterImage;
    private TextView tvTitle;
    private TextView tvSynopsis;
    private TextView tvCriticsScore;
    private TextView tvReleaseDate;
    private TextView trailer;
    private ListView reviews;
    private Button boutonVideo;
    private ArrayAdapter<String> reviewsAdapter;
    private ArrayList<String> reviewsItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        // Fetch views
        ivPosterImage = (ImageView) findViewById(R.id.ivPosterImage);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvSynopsis = (TextView) findViewById(R.id.synopsis);
        tvReleaseDate = (TextView) findViewById(R.id.release_date);
        tvCriticsScore = (TextView) findViewById(R.id.user_rating);
        trailer = (TextView) findViewById(R.id.trailer);
        reviews = (ListView) findViewById(R.id.reviews);
        boutonVideo = (Button) findViewById(R.id.trailer);
        reviewsItems = new ArrayList<String>();
        reviewsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                reviewsItems );
        reviews.setAdapter(reviewsAdapter);

        Movie movie = (Movie) getIntent().getSerializableExtra(MainActivity.MOVIE_DETAIL_KEY);
        loadMovie(movie);
    }

    // Populate the data for the movie
    @SuppressLint("NewApi")
    public void loadMovie(Movie movie) {
        new HttpAsyncTaskGetTrailer().execute("http://api.themoviedb.org/3/movie/" + movie.getId() + "/videos?api_key=7e53348ae448d88502f968a61ae1b9ee");
        new HttpAsyncTaskGetReviews().execute("http://api.themoviedb.org/3/movie/" + movie.getId() + "/reviews?api_key=7e53348ae448d88502f968a61ae1b9ee");

        // Populate data
        tvTitle.setText(movie.getTitle());
        tvSynopsis.setText(movie.getSynopsis());
        tvReleaseDate.setText(movie.getReleaseDate());
        tvCriticsScore.setText(movie.getUserRating());
        Picasso.with(this).load(movie.getPosterUrl()).
                placeholder(R.drawable.large_movie_poster).
                into(ivPosterImage);
    }

    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
            inputStream = httpResponse.getEntity().getContent();
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
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
    private class HttpAsyncTaskGetTrailer extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            JSONObject json = null;
            ArrayList<String> trailerList = new ArrayList();
            String url = "https://www.youtube.com/watch?v=" ;
            try {
                json = new JSONObject(result);
                JSONArray movieJson = json.getJSONArray("results");
                /*for (int i = 0; i < movieJson.length(); i++) {
                    url =  url + movieJson.getJSONObject(i).getString("key");
                    trailerList.add(url);

                }*/
                url =  url + movieJson.getJSONObject(0).getString("key");
                final String abc = url;
                boutonVideo.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent in;
                        if (v.getId() == boutonVideo.getId()) {
                            in = new Intent(Intent.ACTION_VIEW, Uri
                                    .parse(abc));
                            startActivity(in);
                        }

                    }
                });


                trailer.setText("Play Trailer");

            } catch (JSONException e) {
                trailer.setText("Play Trailer");
            }



        }
    }
    private class HttpAsyncTaskGetReviews  extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            reviewsItems.clear();
            JSONObject json = null;
            try {
                json = new JSONObject(result);
                JSONArray movieJson = json.getJSONArray("results");
                for (int i = 0; i < movieJson.length(); i++) {
                    reviewsItems.add(movieJson.getJSONObject(i).getString("content"));

                }

            } catch (JSONException e) {
                reviewsItems.clear();
            }


            reviewsAdapter.notifyDataSetChanged();


        }
    }

}

