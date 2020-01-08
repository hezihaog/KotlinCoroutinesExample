package com.zh.android.kotlincoroutinesexample.ext

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import java.io.File

/**
 * <b>Package:</b> com.zh.android.kotlincoroutinesexample.ext <br>
 * <b>Create Date:</b> 2020-01-08  14:41 <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b>  <br>
 */

/**
 * 加载图片为Bitmap，同步操作
 */
fun String.toBitmap(context: Context): Bitmap {
    return Glide.with(context)
        .asBitmap()
        .load(this)
        .submit()
        .get()
}

/**
 * 加载图片为File，同步操作
 */
fun String.toFile(context: Context): File {
    return Glide.with(context)
        .asFile()
        .load(this)
        .submit()
        .get()
}