package com.example.coroutineshilt

sealed class CatEvent {
    class Success(val catImageUrl: String) : CatEvent()
    class Failure(val errorText: String) : CatEvent()

    object Empty : CatEvent()
}