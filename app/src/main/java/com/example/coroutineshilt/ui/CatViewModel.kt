package com.example.coroutineshilt.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutineshilt.CatEvent
import com.example.coroutineshilt.MainRepository
import com.example.coroutineshilt.util.DispatcherProvider
import com.example.coroutineshilt.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {
    private val mutableStateFlow = MutableStateFlow<CatEvent>(CatEvent.Empty)
    val conversion: StateFlow<CatEvent> = mutableStateFlow

    fun getCat() {
        viewModelScope.launch(dispatchers.io) {
            when (val catResponse = repository.getCats()) {
                is Resource.Error -> mutableStateFlow.value = catResponse.message.let { CatEvent.Failure(it ?: "") }
                is Resource.Success -> {
                    val catImageUrl = catResponse.data?.get(0)?.url ?: ""
                    if (catImageUrl.isEmpty()) {
                        mutableStateFlow.value = CatEvent.Failure("unexpected error")
                    } else {
                        mutableStateFlow.value = CatEvent.Success(catImageUrl)
                    }
                }
            }
        }
    }

}