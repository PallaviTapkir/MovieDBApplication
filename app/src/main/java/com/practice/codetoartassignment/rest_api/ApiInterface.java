package com.practice.codetoartassignment.rest_api;

import com.practice.codetoartassignment.model.response.MovieImagesResponse;
import com.practice.codetoartassignment.model.response.MoviesModelResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by pallavi on 19/9/17.
 */

public interface ApiInterface {

    @GET("movie/upcoming")
    Call<MoviesModelResponse> getMovieList(@Query("api_key") String apiKey);

    /*movie-id*/
    @GET("movie/{movie-id}/images")
    Call<MovieImagesResponse> getMovieImages(@Path("movie-id") long moviewId, @Query("api_key") String apiKey);
}
