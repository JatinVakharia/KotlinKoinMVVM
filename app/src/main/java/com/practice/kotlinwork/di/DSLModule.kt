package com.practice.kotlinwork.di

import com.practice.kotlinwork.api.APIService
import com.practice.kotlinwork.presentation.ItemListViewModel
import com.practice.kotlinwork.presentation.detail.ItemDetailViewModel
import com.practice.kotlinwork.presentation.search.SearchViewModel
import com.practice.kotlinwork.repo.FlickrRepo
import com.practice.kotlinwork.repo.FlickrRepoImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private val SERVER_URL = " https://api.flickr.com/"

val uiModule = module {
    viewModel { ItemListViewModel(get()) }
    viewModel { ItemDetailViewModel() }
    viewModel { SearchViewModel(get()) }
}

val repoModule = module {
    single<FlickrRepo> { FlickrRepoImpl(get()) }
}

val apiModule = module {
    //    provideWebComponents
    single { createOkHttpClient() }

    single { createWebService<APIService>(get(), SERVER_URL) }
}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient.Builder()
            .connectTimeout(10L, TimeUnit.SECONDS)
            .readTimeout(10L, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor).build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    return retrofit.create(T::class.java)
}
