package com.audiohack.boardcast.api;

import com.audiohack.boardcast.model.Collection;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * @since 2015.09.19
 */
public interface BoardCastAPI {

    @GET("/{userId}/collections")
    Call<List<Collection>> listCollections(@Path("userId") int userId);

    @GET("/user/{userId}/collections/{collectionId}")
    Call<Collection> getCollection(@Path("userId") int userId,
                                       @Path("collectionId") int collectionId);
}
