package com.zh.android.kotlincoroutinesexample

import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.zh.android.kotlincoroutinesexample.model.ImageDataModel
import com.zh.android.kotlincoroutinesexample.ui.ImageViewModel
import com.zh.android.kotlincoroutinesexample.ui.item.ImageItemViewBinder
import me.drakeet.multitype.Items
import me.drakeet.multitype.MultiTypeAdapter

class MainActivity : AppCompatActivity() {
    private val vRefreshLayout by lazy {
        findViewById<SmartRefreshLayout>(R.id.refresh_layout)
    }
    private val vRefreshList by lazy {
        findViewById<RecyclerView>(R.id.refresh_list)
    }
    private val vLoadingView by lazy {
        findViewById<ProgressBar>(R.id.loading_view)
    }

    private val mListItem: Items by lazy {
        Items()
    }

    private val mListAdapter by lazy {
        MultiTypeAdapter(mListItem).apply {
            register(ImageDataModel::class.java, ImageItemViewBinder())
        }
    }

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(ImageViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vRefreshLayout.apply {
            setEnableLoadMore(false)
            setOnRefreshListener {
                refresh()
            }
        }
        vRefreshList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mListAdapter
        }
        mViewModel.loadState.observe(this, Observer {
            when (it) {
                LoadState.Loading() -> {
                    vLoadingView.isVisible = true
                }
                LoadState.Success() -> {
                    vLoadingView.isVisible = false
                }
                LoadState.Fail() -> {
                    vLoadingView.isVisible = false
                    vRefreshLayout.finishRefresh(false)
                }
            }
        })
        mViewModel.imageData.observe(this, Observer {
            mListItem.clear()
            mListItem.addAll(it)
            mListAdapter.notifyDataSetChanged()
            vRefreshLayout.finishRefresh()
        })
        vRefreshLayout.autoRefresh()
    }

    private fun refresh() {
        mViewModel.getImageList()
    }
}