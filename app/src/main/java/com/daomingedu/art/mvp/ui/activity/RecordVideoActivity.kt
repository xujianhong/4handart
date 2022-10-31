package com.daomingedu.art.mvp.ui.activity

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.provider.MediaStore

import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.daomingedu.art.R
import com.daomingedu.art.app.Constant
import com.daomingedu.art.app.Preference
import com.daomingedu.art.data.BaseDataHelper
import com.daomingedu.art.mvp.model.LocalWork
import com.daomingedu.art.mvp.model.api.Api
import com.daomingedu.art.mvp.ui.widget.dialog.MyDialog
import com.daomingedu.art.util.MediaFile
import com.daomingedu.art.util.MyOkGoUtil
import com.daomingedu.art.util.SharedPreferencesUtil
import com.daomingedu.art.util.StringUtils
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.LogUtils
import com.jmolsmobile.landscapevideocapture.VideoCaptureActivity
import com.jmolsmobile.landscapevideocapture.configuration.CaptureConfiguration
import com.jmolsmobile.landscapevideocapture.configuration.PredefinedCaptureConfigurations
import com.lzy.okgo.model.HttpParams
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_record_video.*
import org.json.JSONObject
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat

class RecordVideoActivity : AppCompatActivity() {
    private var videoTime by Preference(Constant.VIDEO_TIME,0)
    private var videoPixel by Preference(Constant.VIDEO_PIXEL,0)
    internal var videofile: Long = Long.MAX_VALUE//限制视频文件大小
    internal var helper: BaseDataHelper? = null//数据库
    internal var db: SQLiteDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_video)
        helper = BaseDataHelper(this)
        db = helper?.getWritableDatabase()
        title = "考级视频"
        iv_record.setOnClickListener {
            UploadVideoListActivity.startUploadVideoListActivity(this, 0, 0)
            /*RxPermissions(this).request(
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA)
                .subscribe{
                    if (it){
                        getWaterPrintImg()
                    }else{
                        showMessage("请开启相关权限")
                    }
                }*/
        }
    }

    fun base64ToBitmap(base64Data: String): Bitmap {
        val bytes = Base64.decode(base64Data, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    private fun getWaterPrintImg(){
        val httpParams = HttpParams()
        httpParams.put("key", Constant.KEY)
        MyOkGoUtil.postGetString(this, Api.APP_DOMAIN + "/api/common/getWaterMark", httpParams, object : Handler(){
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (msg.what == 0){
                    val result = msg.obj as String
                    val bitmap = base64ToBitmap(result)
                    if (saveBitmapToSdCard(this@RecordVideoActivity, bitmap, "test")){
                        takeVideo()
                    }
                }else {
                    Toast.makeText(this@RecordVideoActivity, "请检查网络连接是否正常", Toast.LENGTH_SHORT).show()
                }
            }
        })
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

    /**
     * 打开摄像头录像
     */
    private fun takeVideo() {
        val config = createCaptureConfiguration()
        val intent = Intent(this, VideoCaptureActivity::class.java)
        intent.putExtra(VideoCaptureActivity.EXTRA_CAPTURE_CONFIGURATION, config)
        intent.putExtra(VideoCaptureActivity.EXTRA_OUTPUT_FILENAME, "") //文件名称
        intent.putExtra(VideoCaptureActivity.WATER, SharedPreferencesUtil.getWater(this))
        intent.putExtra("videoType", 1)
        startActivityForResult(intent, LocalWork.VIDEO)
    }

    private fun createCaptureConfiguration(): CaptureConfiguration {
        //分辨率
        val resolution =when(videoPixel){
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
        if (resultCode == RESULT_OK) {
            if (requestCode == LocalWork.VIDEO) { //选择录像
                val path =
                    data!!.getStringExtra(VideoCaptureActivity.EXTRA_OUTPUT_FILENAME)
                LogUtils.warnInfo(path)
                val file = File(path)
                LogUtils.debugInfo("filesize：" + file.length() / 1024f / 1024f)
                if (!file.exists()) {
                    showMessage("视频获取失败")
                    return
                }
                if (!MediaFile.isVideoFileType(path)) {
                    showMessage("请选择视频")
                    return
                }
                LogUtils.debugInfo("视频文件大小：" + file.length() + "字节")
                if (file.length() > videofile) { //文件大于
                    showMessage("视频大于+" + (videofile / (1024 * 1024)).toInt() + "M，建议选择视频作品--录像")
                    return
                }
                sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)))
                AlertDialog.Builder(this@RecordVideoActivity)
                    .setTitle("提示")
                    .setMessage("视频拍摄结束，请在手机\"照片\"库里预览查看和管理")
                    .setPositiveButton("确定", object : DialogInterface.OnClickListener{
                        override fun onClick(
                            dialog: DialogInterface?,
                            which: Int
                        ) {
                            dialog?.dismiss()
                            UploadVideoListActivity.startUploadVideoListActivity(this@RecordVideoActivity, 0, 0)
                            /*val albumIntent = Intent(Intent.ACTION_VIEW, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                            startActivity(albumIntent)*/
                        }
                    })
                    .setNegativeButton("取消",null)
                    .create()
                    .show()

                //getVideoName(file)
//                showDialog(path)
            }
        }
    }

    private var sessionId by Preference(Constant.SESSIONID,"")
    private var videoName = ""
    private fun getVideoName(file: File){
        val httpParams = HttpParams()
        httpParams.put("sessionId", sessionId)
        httpParams.put("deviceIMEI", 1)
        MyOkGoUtil.post(this, Api.APP_DOMAIN + "/api/common/getVideoName", httpParams, object : Handler(){
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                when(msg?.what){
                    0 -> {
                        val result = msg.obj as String
                        when(result.isNotEmpty()){
                            true -> {
                                val jsonObject = JSONObject(result)
                                videoName = jsonObject.optString("VideoName")
                                val path =
                                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/Camera")
                                val new_file = File(path, "$videoName.mp4")
                                when(file.renameTo(new_file)){
                                    true -> {
                                        sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(File(path.path + "/" + videoName + ".mp4"))))
                                        AlertDialog.Builder(this@RecordVideoActivity)
                                            .setTitle("提示")
                                            .setMessage("视频拍摄结束，请在手机\"照片\"库里预览查看和管理")
                                            .setPositiveButton("确定", object : DialogInterface.OnClickListener{
                                                override fun onClick(
                                                    dialog: DialogInterface?,
                                                    which: Int
                                                ) {
                                                    dialog?.dismiss()
                                                    UploadVideoListActivity.startUploadVideoListActivity(this@RecordVideoActivity, 0, 0)
                                                    /*val albumIntent = Intent(Intent.ACTION_VIEW, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                                                    startActivity(albumIntent)*/
                                                }
                                            })
                                            .setNegativeButton("取消",null)
                                            .create()
                                            .show()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        })
    }

    /**
     * 添加名称
     *
     * @param path
     */
    private fun showDialog(path: String) {
        val strselect = "select name from contact"
        val cursor = db!!.rawQuery(strselect, null)
        val listname: MutableList<String> =
            ArrayList()
        while (cursor.moveToNext()) {
            listname.add(cursor.getString(0))
        }
        cursor.close()
        val myDialog = MyDialog(this)
        myDialog.setCancelable(false)
        myDialog.setContentView(R.layout.dialog_msg_et)
        val et_name: EditText = myDialog.findView(R.id.et_name)
        val defaultName = "视频" + SimpleDateFormat("MMddHHmmss")
            .format(System.currentTimeMillis())
        et_name.setText(defaultName)
        val btn_cancle: Button = myDialog.findView(R.id.btn_cancle)
        val btn_sure: Button = myDialog.findView(R.id.btn_sure)
        btn_cancle.setOnClickListener { myDialog.dismiss() }
        btn_sure.setOnClickListener(View.OnClickListener {
            val name =
                et_name.text.toString().trim { it <= ' ' }.replace("'", "")
            if (StringUtils.containsEmoji(name)) {
                showMessage("不能输入表情")
                return@OnClickListener
            }
            if (!TextUtils.isEmpty(name)) {
                for (str in listname) {
                    if (str == name) {
                        showMessage("名称重复，请再次输入")
                        et_name.setText("")
                        return@OnClickListener
                    }
                }
                val file = File(path)
                val oldName = file.name
                val newPath = path.replace(oldName, "$name.mp4")
                renameFile(path, newPath)
                //String refreshFile = newPath.substring(0,newPath.indexOf(name+".mp4"));
                MediaScannerConnection.scanFile(
                    this@RecordVideoActivity,
                    arrayOf(newPath, path),
                    arrayOf("video/mp4", "video/mp4")
                ) { path, uri -> Log.i("TAG", "onScanCompleted$path") }

                //往本地数据库里增加一条
                val strinsert =
                    " insert into contact (type,name,path,createtime) values('" + 1 + "','" +
                            name + "','" + newPath + "','" + System.currentTimeMillis() + "')"
                db!!.execSQL(strinsert)
            } else {
                showMessage("请输入名称")
                return@OnClickListener
            }
            myDialog.dismiss()
        })
        myDialog.show()
    }

    /**
     * oldPath 和 newPath必须是新旧文件的绝对路径
     */
    private fun renameFile(oldPath: String, newPath: String) {
        if (TextUtils.isEmpty(oldPath)) {
            return
        }
        if (TextUtils.isEmpty(newPath)) {
            return
        }
        val file = File(oldPath)
        file.renameTo(File(newPath))
    }

    fun showMessage(message: String) {
        ArmsUtils.snackbarText(message)
    }
}
