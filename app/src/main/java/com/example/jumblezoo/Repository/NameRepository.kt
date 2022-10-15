package com.example.jumblezoo.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jumblezoo.api.AnimalService
import com.example.jumblezoo.model.AnimalList

class NameRepository(private val animalService: AnimalService) {

    private val nameLiveData= MutableLiveData<AnimalList>()

    val names:LiveData<AnimalList>
    get()=nameLiveData

    suspend fun getName()
    {
        val result = animalService.getName()
        if(result?.body() != null)
        {
            nameLiveData.postValue(result.body())
        }
    }

}