package com.example.photogallery.model

import com.google.gson.annotations.SerializedName

data class GalleryItem(
    val title: String = "",
    val id: String = "",
    @SerializedName("url_s") val url: String = ""
)