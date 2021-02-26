package com.github.myseries.domain.model

sealed class NetworkException : Exception() {
    object Connection : NetworkException()
    object Server : NetworkException()
}
