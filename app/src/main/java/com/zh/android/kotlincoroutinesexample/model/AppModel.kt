package com.zh.android.kotlincoroutinesexample.model

import android.content.pm.PackageInfo
import android.graphics.drawable.Drawable
import java.io.Serializable

/**
 * <b>Package:</b> com.zh.android.kotlincoroutinesexample.model <br>
 * <b>Create Date:</b> 2020-01-08  11:09 <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b>  <br>
 */
class AppModel(
    /**
     * App名称
     */
    val appName: String,
    /**
     * App包名
     */
    val packageName: String,
    /**
     * App图标
     */
    val icon: Drawable,
    /**
     * App包信息
     */
    val packageInfo: PackageInfo
) : Serializable {
    companion object {
        private const val serialVersionUID = 1L;
    }
}