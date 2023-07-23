package com.neil.castellino.sports

interface ApiCallback<T> {
    fun onSuccess(response: T)
    fun onFailure(errorMessage: String)
}