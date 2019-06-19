package com.practice.kotlinwork.repo

import com.practice.kotlinwork.api.APIService
import com.practice.kotlinwork.model.PhotoMainObject
import io.reactivex.Observable


interface FlickrRepo{
    fun getRecentPhotos() : Observable<PhotoMainObject>

    fun getSearchPhotos(tag : String) : Observable<PhotoMainObject>
}

class FlickrRepoImpl(val apiService: APIService) : FlickrRepo{
    override fun getRecentPhotos(): Observable<PhotoMainObject> {
        return apiService.getRecentPhotos().flatMapObservable {
            return@flatMapObservable Observable.just(it)
        }
    }

    override fun getSearchPhotos(tag: String): Observable<PhotoMainObject> {
        return apiService.getSearchPhotos(tags = tag).flatMapObservable {
            return@flatMapObservable Observable.just(it)
        }
    }
}