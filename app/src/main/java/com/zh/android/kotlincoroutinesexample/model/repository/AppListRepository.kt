package com.zh.android.kotlincoroutinesexample.model.repository

import android.content.Context
import com.zh.android.kotlincoroutinesexample.model.AppModel
import com.zh.android.kotlincoroutinesexample.util.AppUtil

/**
 * <b>Package:</b> com.zh.android.kotlincoroutinesexample.model.repository <br>
 * <b>Create Date:</b> 2020-01-08  10:36 <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b>  <br>
 */
class AppListRepository {
    fun getAppList(context: Context, hasSystemApp: Boolean): List<AppModel> {
        return AppUtil.getAppList(context, hasSystemApp).map {
            AppModel(
                AppUtil.getAppName(context, it),
                it.packageName,
                AppUtil.getAppIcon(context, it),
                it
            )
        }
    }
}