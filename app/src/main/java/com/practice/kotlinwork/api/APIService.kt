package com.practice.kotlinwork.api

import com.practice.kotlinwork.model.PhotoMainObject
import com.practice.kotlinwork.util.Constants
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface APIService {

    @GET("services/rest")
    @Headers("Content-type: application/json")
    fun getRecentPhotos(@Query("method") method : String = "flickr.photos.getRecent",
                        @Query("api_key") apiKey : String = Constants.API_KEY,
                        @Query("format") format : String = "json",
                        @Query("nojsoncallback") nojsoncallback : String = "1") : Single<PhotoMainObject>

    @GET("services/rest")
    @Headers("Content-type: application/json")
    fun getSearchPhotos(@Query("method") method : String = "flickr.photos.search",
                        @Query("tags") tags : String,
                        @Query("api_key") apiKey : String = Constants.API_KEY,
                        @Query("format") format : String = "json",
                        @Query("nojsoncallback") nojsoncallback : String = "1") : Single<PhotoMainObject>

}