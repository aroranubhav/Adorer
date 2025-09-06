package com.maxi.adorer.common

sealed class Resource<out T> {

    data class Success<T>(val data: T) : Resource<T>()

    data class DatabaseError(val errorMessage: String) : Resource<Nothing>()

    data class IOError(val errorMessage: String) : Resource<Nothing>()

    data object UnknownError : Resource<Nothing>()
}