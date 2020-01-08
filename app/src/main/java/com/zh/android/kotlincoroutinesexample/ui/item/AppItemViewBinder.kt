package com.zh.android.kotlincoroutinesexample.ui.item

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zh.android.kotlincoroutinesexample.R
import com.zh.android.kotlincoroutinesexample.model.AppModel
import me.drakeet.multitype.ItemViewBinder

/**
 * <b>Package:</b> com.zh.android.kotlincoroutinesexample.ui.item <br>
 * <b>Create Date:</b> 2020-01-08  11:11 <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b>  <br>
 */
class AppItemViewBinder : ItemViewBinder<AppModel, AppItemViewBinder.ViewHolder>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_app, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, itemModel: AppModel) {
        itemModel.run {
            holder.vIcon.setImageDrawable(icon)
            holder.vAppName.text = appName
            holder.vPackageName.text = packageName
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val vIcon = view.findViewById<ImageView>(R.id.icon)
        val vAppName = view.findViewById<TextView>(R.id.app_name)
        val vPackageName = view.findViewById<TextView>(R.id.package_name)
    }
}