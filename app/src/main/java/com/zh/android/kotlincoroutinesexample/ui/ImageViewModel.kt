package com.zh.android.kotlincoroutinesexample.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zh.android.kotlincoroutinesexample.DownloadState
import com.zh.android.kotlincoroutinesexample.LoadState
import com.zh.android.kotlincoroutinesexample.ext.generateInt
import com.zh.android.kotlincoroutinesexample.ext.launch
import com.zh.android.kotlincoroutinesexample.ext.toFile
import com.zh.android.kotlincoroutinesexample.http.NetworkManager
import com.zh.android.kotlincoroutinesexample.model.ImageDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.io.File

/**
 * <b>Package:</b> com.zh.android.kotlincoroutinesexample.ui <br>
 * <b>Create Date:</b> 2020-01-07  16:21 <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b>  <br>
 */
class ImageViewModel : ViewModel() {
    /**
     * 加载状态
     */
    val loadState = MutableLiveData<LoadState>()

    /**
     * 图片列表
     */
    val imageData = MutableLiveData<List<ImageDataModel>>()

    /**
     * 图片下载状态
     */
    val downloadImageState = MutableLiveData<DownloadState>()

    /**
     * 获取图片列表
     */
    fun getImageList() {
        launch({
            loadState.value = LoadState.Loading()
            //加载图片列表
            imageData.value = mutableListOf<Int>().generateInt(15).map {
                //async表示并发请求，调用await开始协程，如果想串行，则不加async，直接开始协程
                async { NetworkManager.apiService.getImage() }
            }.map {
                it.await()
            }
            loadState.value = LoadState.Success()
        }, { error ->
            loadState.value = LoadState.Fail(error.message)
        })
    }

    /**
     * 下载图片
     * @param url 图片Url
     * @param dirPath 下载目录地址
     * @param fileName 下载文件名称
     */
    fun downloadFile(context: Context, url: String, dirPath: String, fileName: String) {
        launch({
            withContext(Dispatchers.IO) {
                try {
                    val result = url.toFile(
                        context
                    ).copyRecursively(File(dirPath, fileName))
                    withContext(Dispatchers.Main) {
                        if (result) {
                            downloadImageState.value = DownloadState.Success
                        } else {
                            downloadImageState.value = DownloadState.Fail(RuntimeException("下载失败"))
                        }
                    }
                } catch (e: Exception) {
                    downloadImageState.value = DownloadState.Fail(e)
                }
            }
        }, {
            downloadImageState.value = DownloadState.Fail(it)
        })
    }
}