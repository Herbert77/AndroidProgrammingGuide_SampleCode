package com.example.photogallery.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.photogallery.api.FlickrFetcher
import com.example.photogallery.model.GalleryItem

class PhotoGalleryViewModel: ViewModel() {

    val galleryItemLiveData: LiveData<List<GalleryItem>> = FlickrFetcher().fetchPhotos()
}