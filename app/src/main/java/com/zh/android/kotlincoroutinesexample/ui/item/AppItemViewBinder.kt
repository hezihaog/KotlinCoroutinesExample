package com.zh.android.kotlincoroutinesexample.ui.item

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zh.android.kotlincoroutinesexample.R
import com.zh.android.kotlincoroutinesexample.ext.click
import com.zh.android.kotlincoroutinesexample.model.AppModel
import com.zh.android.kotlincoroutinesexample.widget.MoreActionView
import me.drakeet.multitype.ItemViewBinder

/**
 * <b>Package:</b> com.zh.android.kotlincoroutinesexample.ui.item <br>
 * <b>Create Date:</b> 2020-01-08  11:11 <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b>  <br>
 */
class AppItemViewBinder(
    private val clickMoreBlock: (position: Int, itemModel: AppModel, moreAction: MoreActionView) -> Unit
) : ItemViewBinder<AppModel, AppItemViewBinder.ViewHolder>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_app, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, itemModel: AppModel) {
        itemModel.run {
            holder.vIcon.setImageDrawable(icon)
            holder.vAppName.text = appName
            holder.vApkSize.text = itemModel.apkSize
            holder.vPackageName.text = packageName
            holder.vMoreAction.click {
                clickMoreBlock(holder.adapterPosition, itemModel, holder.vMoreAction)
            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val vIcon: ImageView = view.findViewById(R.id.icon)
        val vAppName: TextView = view.findViewById(R.id.app_name)
        val vApkSize: TextView = view.findViewById(R.id.apk_size)
        val vPackageName: TextView = view.findViewById(R.id.package_name)
        val vMoreAction: MoreActionView = view.findViewById(R.id.more_action)
    }
}