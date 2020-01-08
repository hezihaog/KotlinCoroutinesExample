package com.zh.android.kotlincoroutinesexample

import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.wanglu.photoviewerlibrary.OnLongClickListener
import com.wanglu.photoviewerlibrary.PhotoViewer
import com.zh.android.kotlincoroutinesexample.model.ImageDataModel
import com.zh.android.kotlincoroutinesexample.ui.ImageViewModel
import com.zh.android.kotlincoroutinesexample.ui.applist.AppListActivity
import com.zh.android.kotlincoroutinesexample.ui.item.ImageItemViewBinder
import luyao.util.ktx.ext.startKtxActivity
import luyao.util.ktx.ext.toast
import me.drakeet.multitype.Items
import me.drakeet.multitype.MultiTypeAdapter
import java.util.*

class MainActivity : AppCompatActivity() {
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
            register(ImageDataModel::class.java, ImageItemViewBinder { position, itemModel ->
                PhotoViewer.setData(arrayListOf<String>().apply {
                    addAll(
                        mListItem.filterIsInstance<ImageDataModel>().map {
                            it.imgUrl
                        }
                    )
                })
                    .setImgContainer(vRefreshList)
                    .setCurrentPage(position)
                    .setShowImageViewInterface(object : PhotoViewer.ShowImageViewInterface {
                        override fun show(iv: ImageView, url: String) {
                            Glide.with(iv.context).load(url).into(iv)
                        }
                    })
                    .setOnLongClickListener(object : OnLongClickListener {
                        override fun onLongClick(view: View) {
                            AlertDialog.Builder(view.context)
                                .setItems(
                                    mutableListOf<CharSequence>(
                                        "保存到相册"
                                    ).toTypedArray()
                                ) { _, which ->
                                    when (which) {
                                        0 -> {
                                            val context = this@MainActivity.applicationContext
                                            val url = itemModel.imgUrl
                                            mViewModel.downloadFile(
                                                context,
                                                url,
                                                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath,
                                                UUID.randomUUID().toString().replace("-", "")
                                            )
                                        }
                                    }
                                }
                                .create()
                                .show()
                        }
                    })
                    .start(this@MainActivity)
            })
        }
    }

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(ImageViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_refresh_list)
        vRefreshLayout.apply {
            setEnableLoadMore(false)
            setOnRefreshListener {
                refresh()
            }
        }
        vRefreshList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mListAdapter
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
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
        mViewModel.imageData.observe(this, Observer {
            mListItem.clear()
            mListItem.addAll(it)
            mListAdapter.notifyDataSetChanged()
            vRefreshLayout.finishRefresh()
        })
        mViewModel.downloadImageState.observe(this, Observer {
            when (it) {
                is DownloadState.Success -> {
                    toast("保存成功")
                }
            }
        })
        refresh()
    }

    private fun refresh() {
        mViewModel.getImageList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_show_app_list -> {
                startKtxActivity<AppListActivity>()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}