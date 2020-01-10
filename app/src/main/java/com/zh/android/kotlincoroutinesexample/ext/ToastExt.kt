package com.zh.android.kotlincoroutinesexample.ext

import android.content.Context
import android.widget.Toast

/**
 * <b>Package:</b> com.zh.android.kotlincoroutinesexample.ext <br>
 * <b>Create Date:</b> 2020-01-10  09:46 <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b>  <br>
 */

fun Context.toast(str: String) {
    Toast.makeText(this.applicationContext, str, Toast.LENGTH_SHORT).show()
}

fun Context.toastLong(str: String) {
    Toast.makeText(this.applicationContext, str, Toast.LENGTH_LONG).show()
}