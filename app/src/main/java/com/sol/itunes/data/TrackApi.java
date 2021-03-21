package com.sol.itunes.data;

import com.sol.itunes.model.TrackResponse;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TrackApi {
    @GET("/search")
    Single<Response<TrackResponse>> searchTracks(@Query("term") String term, @Query("media") String media);
}
