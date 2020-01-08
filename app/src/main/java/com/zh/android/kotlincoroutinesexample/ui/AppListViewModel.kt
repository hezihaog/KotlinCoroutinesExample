package com.zh.android.kotlincoroutinesexample.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zh.android.kotlincoroutinesexample.LoadState
import com.zh.android.kotlincoroutinesexample.ext.launch
import com.zh.android.kotlincoroutinesexample.model.AppModel
import com.zh.android.kotlincoroutinesexample.model.repository.AppListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * <b>Package:</b> com.zh.android.kotlincoroutinesexample.ui <br>
 * <b>Create Date:</b> 2020-01-08  10:28 <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b>  <br>
 */
class AppListViewModel : ViewModel() {
    /**
     * 加载状态
     */
    val loadState = MutableLiveData<LoadState>()

    /**
     * 已安装的App列表数据
     */
    val appListData = MutableLiveData<List<AppModel>>()

    private val mAppListRepository by lazy {
        AppListRepository()
    }

    /**
     * 获取已安装的App列表
     */
    fun getAppList(context: Context, hasSystemApp: Boolean) {
        launch({
            withContext(Dispatchers.Main) {
                loadState.value = LoadState.Loading()
                withContext(Dispatchers.IO) {
                    val list = mAppListRepository.getAppList(context, hasSystemApp)
                    withContext(Dispatchers.Main) {
                        appListData.value = list
                    }
                }
                loadState.value = LoadState.Success()
            }
        }, { error ->
            loadState.value = LoadState.Fail(error.message)
        })
    }
}