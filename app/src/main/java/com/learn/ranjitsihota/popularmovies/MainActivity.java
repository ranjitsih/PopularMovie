package com.learn.ranjitsihota.popularmovies;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends  Activity {
    private GridView lvMovies;
    private MoviesAdapter adapterMovies;
    //private ThemoviedbClient client;
    public static final String MOVIE_DETAIL_KEY = "movie";
    //EditText etResponse;
    //TextView tvIsConnected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvMovies = (GridView) findViewById(R.id.lvMovies);
        ArrayList<Movie> aMovies = new ArrayList<Movie>();
        adapterMovies = new MoviesAdapter(this, aMovies);
        lvMovies.setAdapter(adapterMovies);
        registerForContextMenu(lvMovies);
        new HttpAsyncTask().execute("http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=7e53348ae448d88502f968a61ae1b9ee&page=1");
    }
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            adapterMovies.clear();

            String sortType = "popularity.desc";
            //String type = item.getTitle().toString();
            if(item.getTitle().toString().equals("Highest-Rated")){
                sortType = "vote_average.desc";
            }
            new HttpAsyncTask().execute("http://api.themoviedb.org/3/discover/movie?sort_by=" + sortType +"&api_key=7e53348ae448d88502f968a61ae1b9ee&page=1");
            adapterMovies.notifyDataSetChanged();
            return true;
        }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("SORT");
        menu.add(0, v.getId(), 0, "Most Popular");//groupId, itemId, order, title
        menu.add(0, v.getId(), 0, "Highest-Rated");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        adapterMovies.clear();

        String sortType = "popularity.desc";
        if(item.getTitle()=="Highest-Rated"){
            sortType = "vote_average.desc";
        }
        new HttpAsyncTask().execute("http://api.themoviedb.org/3/discover/movie?sort_by=" + sortType +"&api_key=7e53348ae448d88502f968a61ae1b9ee&page=1");
        adapterMovies.notifyDataSetChanged();
        return true;
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

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            fetchBoxOfficeMovies(result);
            setupMovieSelectedListener();
        }
    }
    private void fetchBoxOfficeMovies(String result) {
        ArrayList<Movie> movies = new ArrayList<>();
        JSONObject json = null;
        try {
            json = new JSONObject(result);
            JSONArray movieJson = json.getJSONArray("results");
            movies = Movie.fromJson(movieJson);

        }
        catch (Exception e)
        {
            Movie movie =  Movie.from();
            Movie movie2 =  Movie.from2();
            movies.add(movie);
            movies.add(movie2);
        }

        adapterMovies.addAll(movies);


    }


    public void setupMovieSelectedListener() {
        lvMovies.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position, long rowId) {
                Intent i = new Intent(MainActivity.this, DetailActivity.class);
                i.putExtra(MOVIE_DETAIL_KEY, adapterMovies.getItem(position));
                startActivity(i);
            }
        });
    }

}
