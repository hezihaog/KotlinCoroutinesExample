package com.zh.android.kotlincoroutinesexample

/**
 * <b>Package:</b> com.zh.android.kotlincoroutinesexample <br>
 * <b>Create Date:</b> 2020-01-07  16:22 <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b> 加载状态 <br>
 */
sealed class LoadState(val msg: String? = "") {
    /**
     * 加载中
     */
    class Loading(msg: String? = "加载中") : LoadState(msg)

    /**
     * 加载成功
     */
    class Success(msg: String? = "加载成功") : LoadState(msg)

    /**
     * 加载失败
     */
    class Fail(msg: String? = "加载失败") : LoadState(msg)
}