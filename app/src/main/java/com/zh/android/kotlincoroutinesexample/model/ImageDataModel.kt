package com.zh.android.kotlincoroutinesexample.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * <b>Package:</b> com.zh.android.kotlincoroutinesexample.model <br>
 * <b>Create Date:</b> 2020-01-07  16:12 <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b> 图片数据模型 <br>
 */
data class ImageDataModel(
    var code: String,
    @SerializedName("imgurl")
    val imgUrl: String
) : Serializable {
    companion object {
        private const val serialVersionUID = 1L;
    }
}