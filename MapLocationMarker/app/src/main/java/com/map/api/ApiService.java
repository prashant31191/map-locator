package com.map.api;

import com.map.response.NearbysearchListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * Created by Admin on 8/5/2016.
 */

public interface ApiService
{


    @POST("json?")
    Call<NearbysearchListResponse> getNearbyLocation
            (
                    @Query("location") String location,
                    @Query("types") String types,
                    @Query("radius") String radius,
                    @Query("sensor") String sensor,
                    @Query("key") String key
            );


   /*

    asyncCall.webMethod = RequestMethod.GET;
		asyncCall.setUrl(App.WEB_GOOGLE_PLACES_API);
		asyncCall.addParam("location",location);
		asyncCall.addParam("types",types);
		radius=String.valueOf(intRadius);
		asyncCall.addParam("radius",radius);
		asyncCall.addParam("sensor","false");
		asyncCall.addParam("key",getResources().getString(R.string.strGoogleMapKey));



    @GET("/json?")
    void getNearbyLocation
            (

                    @Query("location") String location,
                    @Query("types") String types,
                    @Query("radius") String radius,
                    @Query("sensor") String sensor,
                    @Query("key") String key,
                    Callback<NearbysearchListResponse> cb
            );*/

}
