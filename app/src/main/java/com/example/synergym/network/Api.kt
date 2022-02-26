package com.example.synergym.network

import com.example.synergym.myorders.data.NewsData
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface Api {

    @GET("/v2/everything?q=apple&from=2022-02-24&to=2022-02-24&sortBy=popularity&apiKey=85fdbfea2f694c3c9b488ace16b50186")
    suspend fun myNewsList(): Response<NewsData>

    companion object {
        operator fun invoke(networkConnectionIntercepter: NetworkConnectionIntercepter): Api {
            val okkhttpClient =
                OkHttpClient.Builder().addInterceptor(networkConnectionIntercepter)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build()
            return Retrofit.Builder()
                .client(okkhttpClient)
                .baseUrl("https://newsapi.org")
                .addConverterFactory(GsonConverterFactory.create()).build().create(Api::class.java)
        }
    }
}