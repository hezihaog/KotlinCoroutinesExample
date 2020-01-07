package com.zh.android.kotlincoroutinesexample.ext

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * <b>Package:</b> com.zh.android.kotlincoroutinesexample.ext <br>
 * <b>Create Date:</b> 2020-01-07  16:16 <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b>  <br>
 */

/**
 * 快速开启一个协程，传入成功、异常、完成的回调
 * @param block 执行成功回调
 * @param onError 执行发生异常回调
 * @param onComplete 执行完成回调，成功或异常都会回调
 */
fun ViewModel.launch(
    block: suspend CoroutineScope.() -> Unit,
    onError: (e: Throwable) -> Unit = {},
    onComplete: () -> Unit = {}
) {
    viewModelScope.launch(CoroutineExceptionHandler { _, e ->
        onError(e)
    }) {
        try {
            block(this)
        } finally {
            onComplete()
        }
    }
}