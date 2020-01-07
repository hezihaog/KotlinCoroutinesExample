package com.zh.android.kotlincoroutinesexample.http

import com.zh.android.kotlincoroutinesexample.model.ImageDataModel
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * <b>Package:</b> com.zh.android.kotlincoroutinesexample.http <br>
 * <b>Create Date:</b> 2020-01-07  16:10 <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b>  <br>
 */
interface ApiService {
    /**
     * 随机获取一张图片
     */
    @GET("image/sogou/api.php")
    suspend fun getImage(@Query("type") type: String = "json"): ImageDataModel
}