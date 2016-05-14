package me.benear.core.services;

import java.util.List;

import me.benear.model.BeResource;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by mreverter on 14/5/16.
 */
public interface ResourceService {
    @GET("/resources")
    Call<List<BeResource>> getResourcesCall(@Query("tag_id") String tagId, @Query("types") String typesList);
}