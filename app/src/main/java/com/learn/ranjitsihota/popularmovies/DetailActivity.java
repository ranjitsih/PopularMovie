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
    private TextView tvReleaseDate;


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
        Movie movie = (Movie) getIntent().getSerializableExtra(MainActivity.MOVIE_DETAIL_KEY);
        loadMovie(movie);
    }

    // Populate the data for the movie
    @SuppressLint("NewApi")
    public void loadMovie(Movie movie) {

        // Populate data
        tvTitle.setText(movie.getTitle());
        tvSynopsis.setText(movie.getSynopsis());
        tvReleaseDate.setText(movie.getReleaseDate());
        tvCriticsScore.setText(movie.getUserRating());
        Picasso.with(this).load(movie.getPosterUrl()).
                placeholder(R.drawable.large_movie_poster).
                into(ivPosterImage);
    }

}

