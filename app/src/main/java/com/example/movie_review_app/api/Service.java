package com.example.movie_review_app.api;

import com.example.movie_review_app.model.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {
    @GET("movie/popular?api_key=f073cd04161a18649b124a481a6e9c4a&language=en-US&page=1")
     Call<MoviesResponse> getPopularMovies ();
    @GET("movie/top_rated?api_key=f073cd04161a18649b124a481a6e9c4a&language=en-US&page=1")
    Call<MoviesResponse> getTopRatedMovies ();

}
