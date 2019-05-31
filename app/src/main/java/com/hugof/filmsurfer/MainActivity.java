package com.hugof.filmsurfer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Movie;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnMovieListener {
    //vars
    private TextView MovieExample;
    private static final String TAG = "MainActivity";

    //RecyclerView vars
    private String Image_Base_URL = "http://image.tmdb.org/t/p/w500";
    private ArrayList<String> lMovieNames = new ArrayList<>();
    private ArrayList<String> lImageUrls = new ArrayList<>();

    //Save Vars;
    List<Movies> currentMovies = new ArrayList<>();
    SharedPreferences savedData;
    SharedPreferences.Editor savedDataEditor;
    Gson gson;
    String jsonString;

    //Movie Pages Adpters
    //private List<Genre> movieGenres;


    private void initRecyclerView(ArrayList<String> lMovieNames, ArrayList<String> lImageUrls) {
        RecyclerView recyclerView = findViewById(R.id.ReciclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(lMovieNames, lImageUrls, this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get the spinner from the xml.


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetDbMoviesApi GetMoviesApi = retrofit.create(GetDbMoviesApi.class);


        Call<MoviesList> call = GetMoviesApi.getPopularMovies();




        call.enqueue(new Callback<MoviesList>() {
            @Override
            public void onResponse(Call<MoviesList> call, Response<MoviesList> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Error fetching the movies!!",
                            Toast.LENGTH_LONG).show();
                    //initRecyclerView("Error code" + response.code());
                    return;
                }
                MoviesList movieslist = response.body();
                //MovieExample.setText("Success!");
                currentMovies = movieslist.getMovies();

                //MovieExample.setText("Title: " +currentMovies.get(1).getTitle());
                for (int i = 0; i < currentMovies.size(); i++) {
                    lMovieNames.add(currentMovies.get(i).getTitle());
                    lImageUrls.add(Image_Base_URL + currentMovies.get(i).getPoster_path());
                    initRecyclerView(lMovieNames, lImageUrls);
                }


            }

            @Override
            public void onFailure(Call<MoviesList> call, Throwable t) {
                //MovieExample.setText("Error" + t);
                Toast.makeText(MainActivity.this, "Error fetching the data!! Check your internet Connection!!",
                        Toast.LENGTH_LONG).show();
                loadCurrrentMovies();
                if (loadCurrrentMovies() == true) {

                    //MovieExample.setText("Title: " +currentMovies.get(1).getTitle());
                    for (int i = 0; i < currentMovies.size(); i++) {
                        lMovieNames.add(currentMovies.get(i).getTitle());
                        lImageUrls.add(Image_Base_URL + currentMovies.get(i).getPoster_path());
                        initRecyclerView(lMovieNames, lImageUrls);
                    }
                }

            }
        }); // Function to call Popular Movies.

    }

    //private ViewPager viewpagertest;
    @Override
    protected void onPause() {
        super.onPause();
        Gson gson = new Gson();
        //Save Movie Names

        savedData = getApplicationContext().getSharedPreferences("SavedMovieNames", MODE_PRIVATE);
        savedDataEditor = savedData.edit();
        String jsonString = gson.toJson(currentMovies);
        savedDataEditor.putString("currentMovies", jsonString);


        savedDataEditor.commit();

    }


    @Override
    public void OnMovieClick(int position) {
        //Log.d(TAG, "onMovieClick: clicked:" + position);
        //Toast.makeText(MainActivity.this, "onMovieClick: clicked:" + position,
        //        Toast.LENGTH_LONG).show();



        Intent intent = new Intent(this, MovieSelected.class);
        intent.putExtra("position", position);
        startActivity(intent);

    }

    private boolean loadCurrrentMovies() {
        Gson gson = new Gson();
        savedData = getApplicationContext().getSharedPreferences("SavedMovieNames", MODE_PRIVATE);
        if (savedData.contains("currentMovies")) {
            String empty_list = gson.toJson(new ArrayList<Movies>());
            currentMovies = gson.fromJson(savedData.getString("currentMovies", empty_list), new TypeToken<ArrayList<Movies>>() {
            }.getType());
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Popular:
                Intent intent = new Intent(this, MainActivity.class);

                startActivity(intent);
                finish();
                break;
            case R.id.Top_Rated:

                Intent intentTopRated = new Intent(this, TopRatedMovies.class);

                startActivity(intentTopRated);
                finish();
                break;
            case R.id.Upcoming:
                Intent intentUpcoming = new Intent(this, UpcomingMovies.class);

                startActivity(intentUpcoming);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);

    }




}
