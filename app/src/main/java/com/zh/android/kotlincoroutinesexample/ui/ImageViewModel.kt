package com.zh.android.kotlincoroutinesexample.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zh.android.kotlincoroutinesexample.LoadState
import com.zh.android.kotlincoroutinesexample.ext.generateInt
import com.zh.android.kotlincoroutinesexample.ext.launch
import com.zh.android.kotlincoroutinesexample.http.NetworkManager
import com.zh.android.kotlincoroutinesexample.model.ImageDataModel
import kotlinx.coroutines.async

/**
 * <b>Package:</b> com.zh.android.kotlincoroutinesexample.ui <br>
 * <b>Create Date:</b> 2020-01-07  16:21 <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b>  <br>
 */
class ImageViewModel : ViewModel() {
    /**
     * 图片列表
     */
    val imageData = MutableLiveData<List<ImageDataModel>>()

    /**
     * 加载状态
     */
    val loadState = MutableLiveData<LoadState>()

    /**
     * 获取图片列表
     */
    fun getImageList() {
        launch({
            loadState.value = LoadState.Loading()
            //加载5张图片
            imageData.value = mutableListOf<Int>().generateInt(5).map {
                //async表示并发请求，调用await开始协程，如果想串行，则不加async，直接开始协程
                async { NetworkManager.apiService.getImage() }
            }.map {
                it.await()
            }
            loadState.value = LoadState.Success()
        }, { error ->
            loadState.value = LoadState.Fail(error.message ?: "加载出现异常")
        })
    }
}