package com.example.jumblezoo.api

import com.example.jumblezoo.model.AnimalList
import retrofit2.Response
import retrofit2.http.GET

interface AnimalService {

    @GET("/animal")
    suspend fun getName():Response<AnimalList>
}