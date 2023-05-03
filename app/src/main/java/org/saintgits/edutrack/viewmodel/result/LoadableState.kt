package org.saintgits.edutrack.viewmodel.result

sealed interface LoadableState<R> {
    data class Result<R>(val result: R): LoadableState<R>
    data class Error<R>(val message: String): LoadableState<R>
    class Loading<R> : LoadableState<R>
}