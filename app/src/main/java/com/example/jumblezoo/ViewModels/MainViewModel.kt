package com.example.jumblezoo.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jumblezoo.Repository.NameRepository
import com.example.jumblezoo.model.AnimalList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel(val repository: NameRepository): ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getName()
        }
    }
    val names: LiveData<AnimalList>
    get() = repository.names
}