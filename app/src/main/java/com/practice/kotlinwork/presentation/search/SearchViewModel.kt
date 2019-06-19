package com.practice.kotlinwork.presentation.search

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.practice.kotlinwork.AbstractViewModel
import com.practice.kotlinwork.model.PhotoMainObject
import com.practice.kotlinwork.repo.FlickrRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class SearchViewModel(val repo: FlickrRepo) : AbstractViewModel() {

    val photoMainObject = MutableLiveData<PhotoMainObject>()

    fun getSearchPhotos(tag : String) : LiveData<PhotoMainObject> {
        launch {
            repo.getSearchPhotos(tag)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        photoMainObject.value = it
                    })
        }
        return photoMainObject
    }

}