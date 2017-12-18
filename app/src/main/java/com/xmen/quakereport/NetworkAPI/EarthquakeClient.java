package com.xmen.quakereport.NetworkAPI;

import com.xmen.quakereport.models.EarthquakeList;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface EarthquakeClient {

    @GET("query?format=geojson&starttime=2017-01-01&endtime=2018-01-02&limit=20")
    Call<EarthquakeList> getEarthQuakes();


    @GET("query")
    Call<EarthquakeList> getEarthquakeList(
        @QueryMap Map<String, Object> map
    );
}
