package com.example.lesson11scrolledlistpagination

sealed class PagingData<out T> {
    data class Item<T>(val data: T) : PagingData<T>()

    object Loading : PagingData<Nothing>()
}