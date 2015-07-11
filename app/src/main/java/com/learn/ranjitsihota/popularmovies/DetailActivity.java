package com.learn.ranjitsihota.popularmovies;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
public class DetailActivity extends Activity {
    private ImageView ivPosterImage;
    private TextView tvTitle;
    private TextView tvSynopsis;
    private TextView tvCriticsScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        // Fetch views
        ivPosterImage = (ImageView) findViewById(R.id.ivPosterImage);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvSynopsis = (TextView) findViewById(R.id.tvSynopsis);
        tvCriticsScore = (TextView) findViewById(R.id.tvCriticsScore);
        // Load movie data
        Movie movie = (Movie) getIntent().getSerializableExtra(MainActivity.MOVIE_DETAIL_KEY);
        loadMovie(movie);
    }

    // Populate the data for the movie
    @SuppressLint("NewApi")
    public void loadMovie(Movie movie) {
        if (android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.HONEYCOMB) {
            getActionBar().setTitle(movie.getTitle());
        }
        // Populate data
        tvTitle.setText(movie.getTitle());
        tvCriticsScore.setText(Html.fromHtml("<b>Critics Score:</b> " + movie.getUserRating() + "%"));
        tvSynopsis.setText(Html.fromHtml("<b>Synopsis:</b> " + movie.getSynopsis()));
        // R.drawable.large_movie_poster from
        // http://content8.flixster.com/movie/11/15/86/11158674_pro.jpg -->
        Picasso.with(this).load(movie.getPosterUrl()).
                placeholder(R.drawable.large_movie_poster).
                into(ivPosterImage);
    }

}

