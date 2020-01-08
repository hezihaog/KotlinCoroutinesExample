package com.zh.android.kotlincoroutinesexample.ui.item

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zh.android.kotlincoroutinesexample.R
import com.zh.android.kotlincoroutinesexample.model.ImageDataModel
import luyao.util.ktx.ext.clickN
import me.drakeet.multitype.ItemViewBinder

/**
 * <b>Package:</b> com.zh.android.kotlincoroutinesexample.ui.item <br>
 * <b>Create Date:</b> 2020-01-07  16:53 <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b> 图片条目 <br>
 */
class ImageItemViewBinder(
    private val clickBlock: (position: Int, itemModel: ImageDataModel) -> Unit
) : ItemViewBinder<ImageDataModel, ImageItemViewBinder.ViewHolder>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_image, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, itemModel: ImageDataModel) {
        itemModel.run {
            Glide.with(holder.itemView).load(imgUrl).into(holder.image)
            holder.image.clickN {
                clickBlock(holder.adapterPosition, itemModel)
            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = itemView.findViewById(R.id.image)
    }
}