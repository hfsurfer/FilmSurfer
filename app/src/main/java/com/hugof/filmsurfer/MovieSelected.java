package com.hugof.filmsurfer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.nio.channels.SelectableChannel;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieSelected extends AppCompatActivity implements RecyclerViewAdapter.OnMovieListener{
    //Vars
    List<Movies> currentMovies = new ArrayList<>();
    SharedPreferences savedData;
    SharedPreferences.Editor savedDataEditor;
    Gson gson;
    String jsonString;
    String AssignGenres="";
    private String Image_Base_URL = "http://image.tmdb.org/t/p/w500";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        float currentRating=0;
        int position = intent.getIntExtra("position",0); //if it's a string you stored.
        int type = intent.getIntExtra("type",0);


        loadCurrrentMovies(type);
        setContentView(R.layout.selected_movie);
        TextView textViewSelectedMovie_title=findViewById(R.id.TextView_SelectedMovieTitle);
        textViewSelectedMovie_title.setText( currentMovies.get(position).getTitle());
        TextView textViewSelectedMovie_overview=findViewById(R.id.TextView_SelectedMovieOverview);
        textViewSelectedMovie_overview.setText( currentMovies.get(position).getOverview());
        ImageView imageView_MoviePoster = findViewById(R.id.ImageView_MoviePoster);
        //int id = getResources().getIdentifier(Image_Base_URL + currentMovies.get(position).getPoster_path(), null, null);
        Glide.with(this).load(Image_Base_URL + currentMovies.get(position).getPoster_path()).into(imageView_MoviePoster);
        //imageView_MoviePoster.setImageURI(Uri.parse(Image_Base_URL + currentMovies.get(position).getPoster_path()));

        TextView textViewSelectedMovie_Rating = findViewById(R.id.TextView_MovieRating);
        textViewSelectedMovie_Rating.setText(currentMovies.get(position).getVote_average());


        currentRating=Float.parseFloat(currentMovies.get(position).getVote_average());
        currentRating/=2;

        RatingBar ratingBar_Rating = findViewById(R.id.ratingBar);
        ratingBar_Rating.setRating(currentRating);

        TextView textViewSelectedMovie_Genres = findViewById(R.id.TextView_Genres);
        List<Integer> GenreList;
        GenreList = currentMovies.get(position).getGenreIds();

        AssignGenres=AssignGenres(GenreList);
        textViewSelectedMovie_Genres.setText(AssignGenres);

        TextView textViewSelectedMovie_ReleaseDate = findViewById(R.id.TextView_ReleaseDate);
        textViewSelectedMovie_ReleaseDate.setText(currentMovies.get(position).getRelease_date());
    }

    private void loadCurrrentMovies(int type){
        if (type==0){
        Gson gson = new Gson();
            savedData = getApplicationContext().getSharedPreferences("SavedMovieNames", MODE_PRIVATE);
            if(savedData.contains("currentMovies")) {
                String empty_list = gson.toJson(new ArrayList<Movies>());
                currentMovies = gson.fromJson(savedData.getString("currentMovies", empty_list), new TypeToken<ArrayList<Movies>>() {
                }.getType());
            }

        }
        else if(type==1) {
            Gson gson = new Gson();
            savedData = getApplicationContext().getSharedPreferences("SavedMovieNamesTopRated", MODE_PRIVATE);
            if(savedData.contains("currentMovies_TopRated")) {
                String empty_list = gson.toJson(new ArrayList<Movies>());
                currentMovies = gson.fromJson(savedData.getString("currentMovies_TopRated", empty_list), new TypeToken<ArrayList<Movies>>() {
                }.getType());
            }
        }
        else if(type==2) {
            Gson gson = new Gson();
            savedData = getApplicationContext().getSharedPreferences("SavedMovieNamesUpcoming", MODE_PRIVATE);
            if(savedData.contains("currentMovies_Upcoming")) {
                String empty_list = gson.toJson(new ArrayList<Movies>());
                currentMovies = gson.fromJson(savedData.getString("currentMovies_Upcoming", empty_list), new TypeToken<ArrayList<Movies>>() {
                }.getType());
            }
        }
    }


    public String AssignGenres(List<Integer> GenreList) {
        int aux=0;


        for (int i = 0; i < GenreList.size(); i++) {
            if (GenreList.get(i)==10759) {
                AssignGenres+="Action & Adventure, ";
            }
            else if (GenreList.get(i)==28) {
                AssignGenres+="Action, ";
            }
            else if (GenreList.get(i)==16) {
                AssignGenres+="Animation, ";
            }
            else if (GenreList.get(i)==12) {
                AssignGenres+="Adventure, ";
            }
            else if (GenreList.get(i)==14) {
                AssignGenres+="Fantasy, ";
            }
            else if (GenreList.get(i)==35) {
                AssignGenres+="Comedy, ";
            }
            else if (GenreList.get(i)==80) {
                AssignGenres+="Crime, ";
            }
            else if (GenreList.get(i)==99) {
                AssignGenres+="Documentary, ";
            }
            else if (GenreList.get(i)==18) {
                AssignGenres+="Drama, ";
            }
            else if (GenreList.get(i)==10751) {
                AssignGenres+="Family, ";
            }
            else if (GenreList.get(i)==10762) {
                AssignGenres+="Kids, ";
            }
            else if (GenreList.get(i)==9648) {
                AssignGenres+="Mystery, ";
            }
            else if (GenreList.get(i)==10763) {
                AssignGenres+="News, ";
            }
            else if (GenreList.get(i)==10764) {
                AssignGenres+="Reality, ";
            }
            else if (GenreList.get(i)==10765) {
                AssignGenres+="Sci-Fi & Fantasy, ";
            }
            else if (GenreList.get(i)==10766) {
                AssignGenres+="Soap, ";
            }
            else if (GenreList.get(i)==10797) {
                AssignGenres+="Talk, ";
            }
            else if (GenreList.get(i)==10768) {
                AssignGenres+="War & Politics, ";
            }
            else if (GenreList.get(i)==37) {
                AssignGenres+="Western, ";
            }
            else if (GenreList.get(i)==36) {
                AssignGenres+="History, ";
            }
            else if (GenreList.get(i)==27) {
                AssignGenres+="Horror, ";
            }
            else if (GenreList.get(i)==10402) {
                AssignGenres+="Music, ";
            }
            else if (GenreList.get(i)==10752) {
                AssignGenres+="War, ";
            }
            else if (GenreList.get(i)==53) {
                AssignGenres+="Thriller, ";
            }
            else if (GenreList.get(i)==10749) {
                AssignGenres+="Romance, ";
            }
            else{
                aux+=1;
            }
        }
        if(aux>0){
            AssignGenres+="Entertainment, ";
        }
        AssignGenres=AssignGenres.substring(0,AssignGenres.length()-2);
        return AssignGenres;
    }

    @Override
    public void OnMovieClick(int position) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}
