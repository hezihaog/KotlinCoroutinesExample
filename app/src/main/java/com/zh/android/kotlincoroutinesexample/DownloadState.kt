package com.zh.android.kotlincoroutinesexample

/**
 * <b>Package:</b> com.zh.android.kotlincoroutinesexample <br>
 * <b>Create Date:</b> 2020-01-08  15:20 <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b> 下载状态 <br>
 */
sealed class DownloadState {
    /**
     * 开始下载
     */
    object Begin : DownloadState()

    /**
     * 下载成功
     */
    object Success : DownloadState()

    /**
     * 下载失败
     */
    class Fail(e: Throwable) : DownloadState()
}