package com.hugof.filmsurfer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDbMoviesApi {

    @GET("movie/popular?api_key=03ecd666efd239a343f8bcbf80832546")
    Call<MoviesList> getPopularMovies();

    @GET("movie/top_rated?api_key=03ecd666efd239a343f8bcbf80832546")
    Call<MoviesList> getTopRatedMovies();

    @GET("movie/upcoming?api_key=03ecd666efd239a343f8bcbf80832546")
    Call<MoviesList> getUpcomingMovies();

    @GET("?api_key=03ecd666efd239a343f8bcbf80832546")
    Call<MoviesList> getSelectedMovie();

}
