package com.omdhanwant.youstream.network;

import com.omdhanwant.youstream.models.MoviesResponse;
import com.omdhanwant.youstream.models.SearchResponse;
import com.omdhanwant.youstream.models.TVResponse;
import com.omdhanwant.youstream.models.VideoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIClientHelper {

    @GET("movie/upcoming")
    Call<MoviesResponse> getTheUpcomingMovies(@Query("api_key") String apiKey);

    @GET("tv/popular")
    Call<TVResponse> getTheUpcomingTVShows(@Query("api_key") String apiKey);


    @GET("movie/{movie_id}/videos")
    Call<VideoResponse> getMovieVideos(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

    @GET("tv/{tv_id}/videos")
    Call<VideoResponse> getTVVideos(@Path("tv_id") int tvId, @Query("api_key") String apiKey);

    @GET("search/multi")
    Call<SearchResponse> getSearchResult(@Query("api_key") String apiKey, @Query("query") String query);

}
