package com.zh.android.kotlincoroutinesexample.ui.applist

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.zh.android.kotlincoroutinesexample.LoadState
import com.zh.android.kotlincoroutinesexample.R
import com.zh.android.kotlincoroutinesexample.model.AppModel
import com.zh.android.kotlincoroutinesexample.ui.AppListViewModel
import com.zh.android.kotlincoroutinesexample.ui.item.AppItemViewBinder
import me.drakeet.multitype.Items
import me.drakeet.multitype.MultiTypeAdapter


/**
 * <b>Package:</b> com.zh.android.kotlincoroutinesexample.ui.applist <br>
 * <b>Create Date:</b> 2020-01-08  11:05 <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b>  <br>
 */
class AppListActivity : AppCompatActivity() {
    private val vRefreshLayout by lazy {
        findViewById<SmartRefreshLayout>(R.id.refresh_layout)
    }
    private val vRefreshList by lazy {
        findViewById<RecyclerView>(R.id.refresh_list)
    }
    private val vLoadingView by lazy {
        findViewById<View>(R.id.loading_view)
    }

    private val mListItem: Items by lazy {
        Items()
    }

    private val mListAdapter by lazy {
        MultiTypeAdapter(mListItem).apply {
            register(AppModel::class.java, AppItemViewBinder())
        }
    }

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(AppListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_refresh_list)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.run {
            //显示返回键
            setDisplayHomeAsUpEnabled(true)
            //隐藏图标
            setDisplayShowHomeEnabled(false)
        }
        vRefreshLayout.apply {
            setEnableLoadMore(false)
            setOnRefreshListener {
                refresh()
            }
        }
        vRefreshList.apply {
            layoutManager = LinearLayoutManager(this@AppListActivity)
            adapter = mListAdapter
            addItemDecoration(
                DividerItemDecoration(
                    this@AppListActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
        mViewModel.loadState.observe(this, Observer {
            when (it) {
                is LoadState.Loading -> {
                    vLoadingView.isVisible = true
                }
                is LoadState.Success -> {
                    vLoadingView.isVisible = false
                }
                is LoadState.Fail -> {
                    vLoadingView.isVisible = false
                    vRefreshLayout.finishRefresh(false)
                }
            }
        })
        mViewModel.appListData.observe(this, Observer {
            mListItem.clear()
            mListItem.addAll(it)
            mListAdapter.notifyDataSetChanged()
            vRefreshLayout.finishRefresh()
        })
        refresh()
    }

    private fun refresh() {
        mViewModel.getAppList(this.applicationContext, false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}