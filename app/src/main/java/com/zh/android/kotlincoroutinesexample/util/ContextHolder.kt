package com.zh.android.kotlincoroutinesexample.util

import android.content.Context
import kotlin.properties.Delegates

/**
 * <b>Package:</b> com.zh.android.kotlincoroutinesexample.util <br>
 * <b>Create Date:</b> 2020-01-08  09:51 <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b>  <br>
 */
object ContextHolder {
    var mContext: Context by Delegates.notNull()

    /**
     * 依附Context
     */
    fun attchContext(context: Context) {
        this.mContext = context
    }
}