package com.neil.castellino.sports.network

interface ApiCallback<T> {
    fun onSuccess(response: T)
    fun onFailure(errorMessage: String)
}