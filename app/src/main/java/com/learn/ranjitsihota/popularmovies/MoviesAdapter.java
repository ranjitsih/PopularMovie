package com.learn.ranjitsihota.popularmovies;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class MoviesAdapter extends ArrayAdapter<Movie> {
    public MoviesAdapter(Context context, ArrayList<Movie> aMovies) {
        super(context, 0, aMovies);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Movie movie = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.adapter_item, null);
        }
        // Lookup view for data population
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvCriticsScore = (TextView) convertView.findViewById(R.id.tvCriticsScore);
        ImageView ivPosterImage = (ImageView) convertView.findViewById(R.id.ivPosterImage);
        // Populate the data into the template view using the data object
        tvTitle.setText(movie.getTitle());
        tvCriticsScore.setText("Score: " + movie.getUserRating() + "%");
        Picasso.with(getContext()).load(movie.getPosterUrl()).into(ivPosterImage);
        // Return the completed view to render on screen
        return convertView;
    }

}
