package com.jhomlala.common.repository

import com.jhomlala.common.model.MovieDetails
import com.jhomlala.common.model.SearchResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface OmdbService {
    @GET("/")
    suspend fun searchMovie(@Query("apikey") apiKey: String, @Query("s") title: String,
                            @Query("page") page: Int): SearchResponse

    @GET("/")
    suspend fun getMovie(@Query("apikey") apiKey: String, @Query("i") id: String): MovieDetails


    companion object {
        fun create(): OmdbService {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BASIC
            val okHttpClient = OkHttpClient.Builder().addInterceptor(logging).build()


            return Retrofit.Builder().baseUrl("http://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build().create(OmdbService::class.java)
        }
    }

}