package com.example.minimusiclesson.data.repository

import com.example.minimusiclesson.data.model.Lesson
import com.example.minimusiclesson.data.remote.ApiService
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) {
    suspend fun getLessons(): Response<Map<String, List<Lesson>>> {
        return apiService.getLessons()
    }
}