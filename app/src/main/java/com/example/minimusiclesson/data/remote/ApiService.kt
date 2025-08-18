package com.example.minimusiclesson.data.remote

import com.example.minimusiclesson.data.model.Lesson
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("b/7JF5")
    suspend fun getLessons(
    ): Response<Map<String, List<Lesson>>>
}