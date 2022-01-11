package com.example.poke.presentation.common

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun CoroutineScope.launchWithErrorHandler(
    onError: (Throwable) -> Unit = {},
    block: suspend () -> Unit
): Job =
    launch(CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e("TopFilmsViewModel", throwable.message, throwable)
        onError(throwable)
    }) {
        block()
    }