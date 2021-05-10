package com.example.workwithretrofit2

import com.example.workwithretrofit2.api.NewsApiJSON
import retrofit2.http.GET

interface APIRequest {
    @GET("/v2/top-headlines?sources=bbc-news&apiKey=3d9ab1350f8147638ef11836a980cd56")
    suspend fun getNews () : NewsApiJSON
}