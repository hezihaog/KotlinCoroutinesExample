package com.zh.android.kotlincoroutinesexample.ext

import android.view.View

/**
 * <b>Package:</b> com.zh.android.kotlincoroutinesexample.ext <br>
 * <b>Create Date:</b> 2020-01-10  09:48 <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b>  <br>
 */

fun View.click(block: (view: View) -> Unit) {
    setOnClickListener {
        block(this)
    }
}