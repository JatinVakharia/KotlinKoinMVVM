package com.practice.kotlinwork.presentation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.practice.kotlinwork.AbstractViewModel
import com.practice.kotlinwork.model.PhotoMainObject
import com.practice.kotlinwork.repo.FlickrRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class ItemListViewModel(val flickrRepo: FlickrRepo) : AbstractViewModel() {

    val photoMainObject = MutableLiveData<PhotoMainObject>()

    fun getRecentPhotos() : LiveData<PhotoMainObject>{
        launch {
            flickrRepo.getRecentPhotos()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        photoMainObject.value = it
                    })
        }
        return photoMainObject
    }

}