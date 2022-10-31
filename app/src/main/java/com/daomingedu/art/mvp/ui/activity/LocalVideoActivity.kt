package com.daomingedu.art.mvp.ui.activity

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.daomingedu.art.R
import com.daomingedu.art.adapter.EmptyAdapter
import com.daomingedu.art.adapter.LocalVideoAdapter
import com.daomingedu.art.adapter.VideoListAdapter
import com.daomingedu.art.db.VideoList
import com.daomingedu.art.db.VideoListItem
import com.daomingedu.art.mvp.ui.adapter.LocalAdapter
import kotlinx.android.synthetic.main.activity_upload_video_list.*
import kotlinx.android.synthetic.main.toolbar.*
import org.litepal.LitePal
import java.io.File

/**
 * Description
 * Created by jianhongxu on 2021/12/27
 */
class LocalVideoActivity : AppCompatActivity() {

    lateinit var folder: String

    var adapter: LocalVideoAdapter? = null
    var videoListItems: MutableList<VideoListItem> = mutableListOf()

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("result", 4)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local_video)

        folder = intent.extras?.getString("folder")!!

        toolbar_new_title.text = "视频列表"
        toolbar_new.setNavigationOnClickListener {
            val intent = Intent()
            intent.putExtra("result", 4)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        Log.e("folder", "folder =$folder")
        val videoList = LitePal.where("folder = '$folder'").find(VideoList::class.java)
        if (videoList.isNotEmpty()) {
            videoListItems.clear()
            for (item in videoList) {
                videoListItems.add(VideoListItem(item.path, item.id))
            }
            initialAdapter();


        } else {
            val list = listOf("")
            recyclerview_deep_clean.adapter = EmptyAdapter(this, list)
            recyclerview_deep_clean.layoutManager = LinearLayoutManager(this)
        }
    }


    private fun initialAdapter() {
        adapter = LocalVideoAdapter(videoListItems)
        recyclerview_deep_clean.adapter = adapter
        recyclerview_deep_clean.layoutManager = LinearLayoutManager(this)
        recyclerview_deep_clean.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        adapter?.apply {
            setOnItemChildClickListener { adapter, view, position ->
                when (view.id) {
                    R.id.item_videolist_scan -> {
                        UploadVideoPlayActivity.startUploadVideoPlayActivity(
                            this@LocalVideoActivity,
                            data[position].path
                        )
                    }

                }
            }
        }
    }

    companion object{
        fun startLocalVideoActivity(activity:AppCompatActivity,folder: String = ""){
            val intent = Intent(activity, LocalVideoActivity::class.java)
            intent.putExtra("folder", folder)
            activity.startActivityForResult(intent,0)
        }
    }
}