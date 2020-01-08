package com.zh.android.kotlincoroutinesexample

import android.app.Application
import com.zh.android.kotlincoroutinesexample.util.ContextHolder

/**
 * <b>Package:</b> com.zh.android.kotlincoroutinesexample <br>
 * <b>Create Date:</b> 2020-01-08  09:51 <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b>  <br>
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ContextHolder.attchContext(this)
    }
}