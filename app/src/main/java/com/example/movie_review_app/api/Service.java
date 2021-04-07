package com.example.movie_review_app.api;

import com.example.movie_review_app.model.MoviesResponse;
import com.example.movie_review_app.model.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Service {
    @GET("movie/popular?api_key=f073cd04161a18649b124a481a6e9c4a&language=en-US")
     Call<MoviesResponse> getPopularMovies ();
    @GET("movie/top_rated?api_key=f073cd04161a18649b124a481a6e9c4a&language=en-US")
    Call<MoviesResponse> getTopRatedMovies ();

    @GET("movie/south?api_key=f073cd04161a18649b124a481a6e9c4a&language=en-US")
    Call<MoviesResponse> getSouthMovies () ;

    @GET("movie/now_playing?api_key=f073cd04161a18649b124a481a6e9c4a&language=en-US")
    Call<MoviesResponse> getNowPlayingMovies ();

    @GET("movie/{movie_id}/videos?api_key=f073cd04161a18649b124a481a6e9c4a&language=en-US")
     Call<TrailerResponse> getMovieTrailer(@Path("movie_id") int id);




}
