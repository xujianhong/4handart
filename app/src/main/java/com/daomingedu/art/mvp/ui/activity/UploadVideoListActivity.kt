package com.daomingedu.art.mvp.ui.activity

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.daomingedu.art.R
import com.daomingedu.art.adapter.EmptyAdapter
import com.daomingedu.art.adapter.VideoListAdapter
import com.daomingedu.art.app.Constant
import com.daomingedu.art.app.Preference
import com.daomingedu.art.db.VideoList
import com.daomingedu.art.db.VideoListItem
import com.daomingedu.art.mvp.model.LocalWork
import com.daomingedu.art.mvp.model.api.Api
import com.daomingedu.art.util.MemoryUtil
import com.daomingedu.art.util.MyOkGoUtil
import com.daomingedu.art.util.SharedPreferencesUtil
import com.google.gson.Gson
import com.jmolsmobile.landscapevideocapture.VideoCaptureActivity
import com.jmolsmobile.landscapevideocapture.configuration.CaptureConfiguration
import com.jmolsmobile.landscapevideocapture.configuration.PredefinedCaptureConfigurations
import com.lzy.okgo.model.HttpParams
import com.tbruyelle.rxpermissions2.RxPermissions
import com.vincent.videocompressor.VideoCompress
import kotlinx.android.synthetic.main.activity_upload_video_list.*
import kotlinx.android.synthetic.main.toolbar.*
import net.alhazmy13.mediapicker.Video.VideoPicker
import org.litepal.LitePal
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class UploadVideoListActivity : AppCompatActivity() {

    private var videoTime by Preference(Constant.VIDEO_TIME, 0)
    private var videoPixel by Preference(Constant.VIDEO_PIXEL, 0)

    lateinit var folder: String
    var videoNum: Int = 0

    var adapter: VideoListAdapter? = null
    var videoListItems: MutableList<VideoListItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_video_list)

        folder = intent.extras?.getString("folder")!!
        videoNum = intent.extras?.getInt("videoNum")!!


        toolbar_new_title.text = "视频列表"
        toolbar_new.setNavigationOnClickListener { finish() }
        type = intent.extras?.getInt("type")!!
        val import = intent.extras?.getInt("import")!!
        when (type) {
            0 -> {
                when (SharedPreferencesUtil.getIsShowVideo(this)) {
                    1 -> upload_video_list_record.visibility = View.VISIBLE
                    0 -> upload_video_list_record.visibility = View.GONE
                }
            }
            1 -> {
                upload_video_list_record.setImageResource(R.drawable.btn_import_local)
                when (import) {
                    1 -> upload_video_list_record.visibility = View.VISIBLE
                    0 -> upload_video_list_record.visibility = View.GONE
                }
            }
        }

        val limit = SharedPreferencesUtil.getLimit(this)
        upload_video_list_record.setOnClickListener {
            when (type) {
                0 -> {
                    RxPermissions(this).request(
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                    )
                        .subscribe {
                            if (it) {
                                when (limit == 0 || list_result.size < limit) {
                                    true -> getWaterPrintImg()
                                    false -> Toast.makeText(
                                        this,
                                        "本地缓存视频数量已超上线",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                Toast.makeText(this, "请开启相关权限", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
                1 -> {
                    VideoPicker.Builder(this)
                        .mode(VideoPicker.Mode.GALLERY)
                        .directory(VideoPicker.Directory.DEFAULT)
                        .extension(VideoPicker.Extension.MP4)
                        .enableDebuggingMode(true)
                        .build()
                }
            }
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

        tv_sure.setOnClickListener {
            if (videoListItems.size <= 0)
                return@setOnClickListener

            var path: String? = null

            for (videoListItem in videoListItems) {
                if (videoListItem.isSelect) {
                    path = videoListItem.path
                    break
                }
            }
            if (path != null) {
                val intent = Intent()
                intent.putExtra("result", path)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }

//        val file = getExternalFilesDir("Movies")
//        if (file != null) {
//            if (file.exists()){
//                val list = file.listFiles()
//                if (list != null){
//                    for (item in list){
//                        if (item.name.endsWith(".mp4")){
//                            list_result.add(item)
//                        }
//                    }
//
//                    if (list_result.isNotEmpty()){
//                        list_result.reverse()
//                        recyclerview_deep_clean.adapter = VideoAdapter(this, list_result, type)
//                        recyclerview_deep_clean.layoutManager = GridLayoutManager(this, 4)
//                    }else{
//                        val list = listOf("")
//                        recyclerview_deep_clean.adapter = EmptyAdapter(this, list)
//                        recyclerview_deep_clean.layoutManager = LinearLayoutManager(this)
//                    }
//                }
//            }
//        }
    }

    private fun initialAdapter() {
        adapter = VideoListAdapter(videoListItems)
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
                            this@UploadVideoListActivity,
                            data[position].path
                        )
                    }
                    R.id.item_videolist_delete -> {
                        val dialog = AlertDialog.Builder(this@UploadVideoListActivity)
                        dialog.setMessage("确定删除视频?")
                        dialog.setPositiveButton("确定", object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface?, which: Int) {

                                LitePal.delete(VideoList::class.java, data[position].id.toLong())
                                val file = File(data[position].path)
                                if (file.exists())
                                    file.delete()

                                data.removeAt(position)


                                notifyItemRemoved(position)
                                notifyItemRangeChanged(0, data.size)
                                dialog?.dismiss()
                            }
                        })
                        dialog.setNegativeButton("取消", object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface?, which: Int) {
                                dialog?.dismiss()
                            }
                        })
                        dialog.create().show()
                    }
                }
            }
            setOnItemClickListener { adapter, view, position ->
                for (datum in data) {
                    datum.isSelect = false
                }
                data[position].isSelect = true
                notifyDataSetChanged()
            }
        }
    }


    fun base64ToBitmap(base64Data: String): Bitmap {
        val bytes = Base64.decode(base64Data, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    private fun getWaterPrintImg() {
        val httpParams = HttpParams()
        httpParams.put("key", Constant.KEY)
        MyOkGoUtil.postGetString(
            this,
            Api.APP_DOMAIN + "/api/common/getWaterMark",
            httpParams,
            object : Handler() {
                override fun handleMessage(msg: Message) {
                    super.handleMessage(msg)
                    if (msg.what == 0) {
                        val result = msg.obj as String
                        val bitmap = base64ToBitmap(result)
                        if (saveBitmapToSdCard(this@UploadVideoListActivity, bitmap, "test")) {
                            when (MemoryUtil.memoryIsAvailble(this@UploadVideoListActivity)) {
                                0 -> takeVideo("")
                                1 -> {
                                    val dialog = AlertDialog.Builder(this@UploadVideoListActivity)
                                    dialog.setMessage("存储空间过小, 可能会出现录制完了无法保存视频的情况, 是否继续拍摄视频")
                                    dialog.setCancelable(false)
                                    dialog.setPositiveButton(
                                        "继续",
                                        object : DialogInterface.OnClickListener {
                                            override fun onClick(
                                                dialog: DialogInterface?,
                                                which: Int
                                            ) {
                                                dialog?.dismiss()
                                                takeVideo("")
                                            }
                                        })
                                    dialog.setNegativeButton(
                                        "取消",
                                        object : DialogInterface.OnClickListener {
                                            override fun onClick(
                                                dialog: DialogInterface?,
                                                which: Int
                                            ) {
                                                dialog?.dismiss()
                                            }
                                        })
                                    dialog.create().show()
                                }
                            }
                        }
                    } else {
                        Toast.makeText(
                            this@UploadVideoListActivity,
                            "请检查网络连接是否正常",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
    }

    private var type = 0
    internal var videofile: Long = Long.MAX_VALUE//限制视频文件大小

    private fun createCaptureConfiguration(): CaptureConfiguration {
        //分辨率
        val resolution = when (videoPixel) {
            1 -> PredefinedCaptureConfigurations.CaptureResolution.RES_480P
            2 -> PredefinedCaptureConfigurations.CaptureResolution.RES_720P
            3 -> PredefinedCaptureConfigurations.CaptureResolution.RES_1080P
            else -> PredefinedCaptureConfigurations.CaptureResolution.RES_480P
        }
        //质量
        val quality = PredefinedCaptureConfigurations.CaptureQuality.LOW //低质量
        //限制时长
        //val fileDuration = videoTime * 60 //10分钟
        val fileDuration = videoTime
        //限制大小
        val filesize = CaptureConfiguration.NO_FILESIZE_LIMIT //不限
        //true显示录制视频时的时间，false为不显示
        val builder =
            CaptureConfiguration.Builder(resolution, quality)
        builder.maxDuration(fileDuration)
//        builder.maxFileSize(((application as MyApp).getVideo() / 1024 / 1024) as Int)
        builder.frameRate(PredefinedCaptureConfigurations.FPS_30)
        builder.noCameraToggle()
        builder.showRecordingTime()
        return builder.build()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode == Activity.RESULT_OK) {
            true -> {
                when (requestCode) {
                    LocalWork.VIDEO -> {
//                        val file = getExternalFilesDir("Movies")
//                        if (file != null) {
//                            if (file.exists()) {
//                                val list = file.listFiles()
//                                if (list != null) {
//                                    list_result.clear()
//                                    for (item in list) {
//                                        if (item.name.endsWith(".mp4")) {
//                                            list_result.add(item)
//                                        }
//                                    }
//                                    list_result.reverse()
//                                    recyclerview_deep_clean.adapter =
//                                        VideoAdapter(this, list_result, type)
//                                    recyclerview_deep_clean.layoutManager =
//                                        GridLayoutManager(this, 4)
//                                }
//                            }
//                        }
                    }
                    VideoPicker.VIDEO_PICKER_REQUEST_CODE -> {
                        val list = data?.getStringArrayListExtra(VideoPicker.EXTRA_VIDEO_PATH)!!
                        Log.d("activityResult", Gson().toJson(list))
                        finishCompleted(list[0])
                    }
                }
            }
        }
    }

    var progressDialog: ProgressDialog? = null

    val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 0) {

                val videoList = LitePal.where("folder = '$folder'").find(VideoList::class.java)
                if (videoList.isNotEmpty()) {
                    videoListItems.clear()
                    for (item in videoList) {
                        videoListItems.add(VideoListItem(item.path, item.id))
                    }
                    if (adapter == null) {
                        initialAdapter()
                    } else {

                        adapter!!.notifyDataSetChanged()
                    }


                } else {
                    val list = listOf("")
                    recyclerview_deep_clean.adapter =
                        EmptyAdapter(this@UploadVideoListActivity, list)
                    recyclerview_deep_clean.layoutManager =
                        LinearLayoutManager(this@UploadVideoListActivity)
                }

//                val file = getExternalFilesDir("Movies")
//                if (file != null) {
//                    if (file.exists()){
//                        val list = file.listFiles()
//                        if (list != null){
//                            list_result.clear()
//                            for (item in list){
//                                if (item.name.endsWith(".mp4")){
//                                    list_result.add(item)
//                                }
//                            }
//                            list_result.reverse()
//                            recyclerview_deep_clean.adapter = VideoAdapter(this@UploadVideoListActivity, list_result, type)
//                            recyclerview_deep_clean.layoutManager = GridLayoutManager(this@UploadVideoListActivity, 4)
//                        }
//                    }
//                }
            }
        }
    }

    private fun finishCompleted(path: String) {
        progressDialog = ProgressDialog(this)
        progressDialog?.setCancelable(false)
        progressDialog?.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        progressDialog?.setMessage("视频压缩中")
        progressDialog?.show()
        /*AlertDialog.Builder builder = new AlertDialog.Builder(VideoCaptureActivity.this);
            builder.setCancelable(false);
            builder.setMessage("视频生成中...");
            alertDialog = builder.create();
            alertDialog.show();*/
        val compressPath =
            (getExternalFilesDir("Movies")?.absolutePath ?: "") + File.separator + "VID_" + getTime(
                System.currentTimeMillis()
            ) + ".mp4"




        VideoCompress.compressVideoMedium(
            path,
            compressPath,
            object : VideoCompress.CompressListener {
                override fun onSuccess() {
                    Log.d("test", "onSuccess")
                    progressDialog?.dismiss()

                    if (videoNum == 1) {
                        val video = LitePal.where(
                            "folder = '$folder'"
                        ).find(VideoList::class.java)
                        for (item in video) {
                            File(item.path).let {
                                if (it.exists()) {
                                    it.delete()
                                }
                            }
                        }
                        LitePal.deleteAll(VideoList::class.java, "folder= ?", folder)
                    }
                    val videolist = VideoList(compressPath, folder)
                    videolist.save()

                    handler.sendEmptyMessage(0)
                }

                override fun onFail() {
                    Log.d("test", "onFail")
                    progressDialog?.dismiss()
                    Toast.makeText(progressDialog?.context,"导入失败",Toast.LENGTH_SHORT).show()
                }

                override fun onProgress(percent: Float) {
                    progressDialog?.max = 100
                    progressDialog?.progress = percent.toInt()
                }

                override fun onStart() {
                }

            })
    }

    private fun getTime(date_temp: Long): String? {
        //时间格式,HH是24小时制，hh是AM PM12小时制
        val sdf = SimpleDateFormat("yyyy-MM-dd_HH_mm")
        //比如timestamp=1449210225945；
        val date_string = sdf.format(Date(date_temp))
        //至于取10位或取13位，date_temp*1000L就是这种截取作用。如果是和服务器传值的，就和后台商量好就可以了
        Log.d("test", date_string)
        return date_string
    }

    /**
     * 打开摄像头录像
     */
    private fun takeVideo(fileName: String) {
        val config = createCaptureConfiguration()
        val intent = Intent(this, VideoCaptureActivity::class.java)
        intent.putExtra(VideoCaptureActivity.EXTRA_CAPTURE_CONFIGURATION, config)
        intent.putExtra(VideoCaptureActivity.EXTRA_OUTPUT_FILENAME, fileName) //文件名称
        intent.putExtra(VideoCaptureActivity.WATER, SharedPreferencesUtil.getWater(this))
        intent.putExtra("videoType", 1)
        startActivityForResult(intent, LocalWork.VIDEO)
    }

    fun saveBitmapToSdCard(
        context: Context,
        mybitmap: Bitmap,
        name: String
    ): Boolean {
        var result = false
        //创建位图保存目录
        val path =
            Environment.getExternalStorageDirectory().toString() + "/test/"
        val sd = File(path)
        if (!sd.exists()) {
            sd.mkdir()
        }
        val file = File("$path$name.png")
        var fileOutputStream: FileOutputStream? = null
        try {
            // 判断SD卡是否存在，并且是否具有读写权限
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                fileOutputStream = FileOutputStream(file)
                mybitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
                fileOutputStream.flush()
                fileOutputStream.close()

                //update gallery
                val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                val uri = Uri.fromFile(file)
                intent.data = uri
                context.sendBroadcast(intent)
                //Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
                result = true
            } else {
                Toast.makeText(context, "不能读取到SD卡", Toast.LENGTH_SHORT).show()
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return result
    }

    private val list_result = ArrayList<File>()

    companion object {

        fun startUploadVideoListActivity(
            activity: AppCompatActivity, type: Int, import: Int, folder: String = "",
            videoNum: Int = 0
        ) {
            val intent = Intent(activity, UploadVideoListActivity::class.java)
            intent.putExtra("type", type)
            intent.putExtra("import", import)
            intent.putExtra("folder", folder)
            intent.putExtra("videoNum", videoNum)
            activity.startActivityForResult(intent, 100)
        }
    }
}