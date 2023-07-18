package com.example.photogallery.api

import com.example.photogallery.model.FlickrResponse
import retrofit2.Call
import retrofit2.http.GET

interface FlickrApi {

//    @GET("/")
//    fun fetchContents(): Call<String>

    @GET("services/rest/?method=flickr.interestingness.getList" +
    "&api_key=d1077494f23e962b758455d354c9f816" +
    "&format=json" +
    "&nojsoncallback=1" +
    "&extras=url_s")
    fun fetchPhotos(): Call<FlickrResponse>

}