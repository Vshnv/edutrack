package org.saintgits.edutrack.model

import java.lang.Exception

sealed interface ApiResult<T> {
    data class Error<T>(val exception: Exception?): ApiResult<T>
    data class Success<T>(val data: T): ApiResult<T>
}