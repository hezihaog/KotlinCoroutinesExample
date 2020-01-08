package com.zh.android.kotlincoroutinesexample.http

import com.zh.android.kotlincoroutinesexample.http.interceptor.HttpLoggingInterceptor
import com.zh.android.kotlincoroutinesexample.util.ContextHolder
import com.zh.android.kotlincoroutinesexample.util.AppUtil
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * <b>Package:</b> com.zh.android.kotlincoroutinesexample.http <br>
 * <b>Create Date:</b> 2020-01-07  16:28 <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b> 网络管理类 <br>
 */
object NetworkManager {
    /**
     * Log拦截器
     */
    private val mLogInterceptor by lazy {
        val logTag = AppUtil.getApplicationName(ContextHolder.mContext)
        HttpLoggingInterceptor(logTag).apply {
            setPrintLevel(HttpLoggingInterceptor.Level.BODY)
            setColorLevel(java.util.logging.Level.INFO)
        }
    }

    /**
     * Retrofit实例
     */
    private val mRetrofit by lazy {
        Retrofit.Builder()
            .client(
                OkHttpClient.Builder().callTimeout(5, TimeUnit.SECONDS)
                    .addInterceptor(mLogInterceptor).build()
            )
            .baseUrl("https://api.ooopn.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * 接口实例
     */
    val apiService: ApiService by lazy {
        mRetrofit.create<ApiService>(ApiService::class.java)
    }
}