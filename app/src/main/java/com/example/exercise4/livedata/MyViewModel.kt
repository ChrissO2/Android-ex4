package com.example.exercise4.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exercise4.data.Champion
import kotlinx.coroutines.launch

class MyViewModel(private val repository: ListRepository) : ViewModel() {
    private val _items = MutableLiveData<List<Champion>>()
    val items: LiveData<List<Champion>> get() = _items

    init {
        viewModelScope.launch {
            _items.value = repository.getChampions()
        }
    }

    fun updateChampion(id: Int, name: String, description: String, lane: Int, rating: Float) {
        viewModelScope.launch {
            repository.updateChampion(id, name, description, lane, rating)
            _items.value = repository.getChampions()
        }
    }

    fun deleteChampionWithId(id: Int) {
        viewModelScope.launch {
            repository.deleteChampionWithId(id)
            _items.value = repository.getChampions()
        }
    }

    fun deleteChampion(item: Champion) {
        viewModelScope.launch {
            repository.deleteChampion(item)
            _items.value = repository.getChampions()
        }
    }

    fun upsetChampion(item: Champion) {
        viewModelScope.launch {
            repository.upsetChampion(item)
            _items.value = repository.getChampions()
        }
    }

    fun addChampion(champion: Champion) {
        viewModelScope.launch {
            repository.upsetChampion(champion)
            _items.value = repository.getChampions()
        }
    }
}