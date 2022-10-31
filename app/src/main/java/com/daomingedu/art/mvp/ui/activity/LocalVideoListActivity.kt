package com.daomingedu.art.mvp.ui.activity

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.DialogInterface
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Bundle

import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.daomingedu.art.R
import com.daomingedu.art.app.Constant
import com.daomingedu.art.app.Preference
import com.daomingedu.art.data.BaseDataHelper
import com.daomingedu.art.di.component.DaggerLocalVideoListComponent
import com.daomingedu.art.di.module.LocalVideoListModule
import com.daomingedu.art.mvp.contract.LocalVideoListContract
import com.daomingedu.art.mvp.model.LocalWork
import com.daomingedu.art.mvp.presenter.LocalVideoListPresenter
import com.daomingedu.art.mvp.ui.adapter.LocalAdapter
import com.daomingedu.art.mvp.ui.widget.PopLocalMenu2
import com.daomingedu.art.mvp.ui.widget.dialog.MyDialog
import com.daomingedu.art.util.MediaFile
import com.daomingedu.art.util.MemoryUtil
import com.daomingedu.art.util.SharedPreferencesUtil
import com.daomingedu.art.util.StringUtils
import com.daomingedu.ijkplayertest.DensityUtil
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.LogUtils
import com.jmolsmobile.landscapevideocapture.VideoCaptureActivity
import com.jmolsmobile.landscapevideocapture.configuration.CaptureConfiguration
import com.jmolsmobile.landscapevideocapture.configuration.PredefinedCaptureConfigurations
import com.jmolsmobile.landscapevideocapture.configuration.PredefinedCaptureConfigurations.CaptureQuality
import com.jmolsmobile.landscapevideocapture.configuration.PredefinedCaptureConfigurations.CaptureResolution
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_local_work.*
import kotlinx.android.synthetic.main.base_no_data.*
import kotlinx.android.synthetic.main.include_page_head.*
import me.jessyan.autosize.internal.CancelAdapt
import org.jetbrains.anko.startActivity
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/27/2020 23:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
/**
 * 如果没presenter
 * 你可以这样写
 *
 * @ActivityScope(請注意命名空間) class NullObjectPresenterByActivity
 * @Inject constructor() : IPresenter {
 * override fun onStart() {
 * }
 *
 * override fun onDestroy() {
 * }
 * }
 */
class LocalVideoListActivity : BaseActivity<LocalVideoListPresenter>(),
    LocalVideoListContract.View,CancelAdapt, View.OnClickListener {
    private var videoTime by Preference(Constant.VIDEO_TIME,0)
    private var videoPixel by Preference(Constant.VIDEO_PIXEL,0)
    internal var popLocalMenu: PopLocalMenu2?=null//添加菜单

    internal var helper: BaseDataHelper? = null//数据库
    internal var db: SQLiteDatabase? = null
    internal var adapter: LocalAdapter? =null

    internal var works: MutableList<LocalWork> = ArrayList<LocalWork>()//本地作品数据

    internal var isUpdate = false//true为上传作业跳转而来；

    internal var isShare = false//是否分享跳转而来

    internal var videofile: Long = Long.MAX_VALUE//限制视频文件大小

    private var isShow: Boolean = false //批量删除是否显示

    //    private int checkedCount;//已选择的个数
    internal var selectedWorks: MutableList<LocalWork> = ArrayList<LocalWork>()

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerLocalVideoListComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .localVideoListModule(LocalVideoListModule(this))
            .build()
            .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_local_video_list //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        tv_name.text = "本地作品"
        val toolbar:androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar2)
        toolbar?.background?.alpha = 255
        toolbar?.setNavigationIcon(R.drawable.ic_back_white)
        toolbar?.setNavigationOnClickListener { killMyself() }
        toolbar?.inflateMenu(R.menu.menu_class_add)
        toolbar?.setOnMenuItemClickListener {
            if (it.itemId == R.id.add){//添加
                popLocalMenu = PopLocalMenu2(this, this, isShare)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    popLocalMenu?.setElevation(10f)
                }
                popLocalMenu?.showAsDropDown(toolbar2, 0,0, Gravity.END)
            }
            return@setOnMenuItemClickListener false
        }

        helper = BaseDataHelper(this)
        db = helper?.getWritableDatabase()
        adapter = LocalAdapter(this)
        adapter?.addOnShowListner(object : LocalAdapter.OnShowListener {
            override fun show(cb_del: CheckBox) {
                if (isShow) {
                    cb_del.visibility = View.VISIBLE
                } else
                    cb_del.visibility = View.GONE
            }
        })
        adapter?.setList(works)
        lv_work.setAdapter(adapter)
        lv_work.setOnItemClickListener(object : AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, i: Int, id: Long) {
                if (isShow) {
                    if (works[i].checked) {
                        works[i].isChecked = false
                        selectedWorks.remove(works[i])
                    } else {
                        works[i].isChecked = true
                        selectedWorks.add(works[i])
                    }
                    changeCheckAllButton()
                    adapter?.notifyDataSetChanged()
                    return
                }
                if (!File(works[i].path).exists()) {//无法播放的时候
                    ArmsUtils.makeText(application,"该作品无法播放")
                    val strdelte = "delete from contact where _id=" + works[i].id//删除本地数据库这一条
                    db?.execSQL(strdelte)
                    works.removeAt(i)
                    adapter?.notifyDataSetChanged()
                } else {
                    val bundle = Bundle()

                    bundle.putSerializable("work", works[i])
                    if (isUpdate) {//选择本地作品
                        val work = bundle.getSerializable("work") as LocalWork
                        if (work.type !== 1 && work.type !== 2) {//判断不是选择的视频，type都为2(录音的类型)
                            work.type = 2
                            bundle.putSerializable("work", work)
                        }
                        val intent = Intent()
                        intent.putExtras(bundle)
                        setResult(RESULT_OK, intent)
                        finish()
                    } else {//查看本地作品详情
                        if (isShare) {
                            val work = bundle.getSerializable("work") as LocalWork
                            if (work.type !== 1 && work.type !== 2) {//判断不是选择的视频，type都为2(录音的类型)
                                work.type = 2
                                bundle.putSerializable("work", work)
                            }
                            val intent = Intent()
                            intent.putExtras(bundle)
                            setResult(RESULT_OK, intent)
                            finish()
                        } else {
//                    bd.jump(LocalInfoAct::class.java, bundle)
                            val intent2 = Intent(this@LocalVideoListActivity,LocalVideoDetailActivity::class.java)
                            intent2.putExtra("work", works[i])
                            startActivity(intent2)
                        }
                    }
                }
            }
        })
        lv_work.setOnItemLongClickListener(object :AdapterView.OnItemLongClickListener{
            override fun onItemLongClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long): Boolean {
                if (!isShow) {
                    works[position].isChecked = true
                    selectedWorks.add(works[position])
                    changeCheckAllButton()
                    adapter?.notifyDataSetChanged()
                    setShow(true)
                    return true
                }
                return false
            }
        })
        bt_cancel.setOnClickListener(this)
        bt_all.setOnClickListener(this)
        bt_del.setOnClickListener(this)
        val bundle = intent.extras
        if (bundle != null) {
            isUpdate = intent.extras?.getBoolean("isUpdate", false)?:false//初始化判断跳转数据
            isShare = intent.extras?.getBoolean("isShare", false)?:false//初始化是否分享数据
        }
    }


    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showMessage(message: String) {
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {
        finish()
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.pop_video -> {//添加视频
                RxPermissions(this).request(
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA)
                    .subscribe{
                        if (it){
                            when (MemoryUtil.memoryIsAvailble(this)){
                                0 -> takeVideo()
                                1 -> {
                                    val dialog = AlertDialog.Builder(this)
                                    dialog.setMessage("存储空间过小, 可能会出现录制完了无法保存视频的情况, 是否继续拍摄视频")
                                    dialog.setCancelable(false)
                                    dialog.setPositiveButton("继续", object : DialogInterface.OnClickListener{
                                        override fun onClick(dialog: DialogInterface?, which: Int) {
                                            dialog?.dismiss()
                                            takeVideo()
                                        }
                                    })
                                    dialog.setNegativeButton("取消", object : DialogInterface.OnClickListener{
                                        override fun onClick(dialog: DialogInterface?, which: Int) {
                                            dialog?.dismiss()
                                        }
                                    })
                                    dialog.create().show()
                                }
                            }
                        }else{
                            showMessage("请开启相关权限")
                        }
                    }
                popLocalMenu?.dismiss()
            }
            R.id.pop_recording -> {//添加钢琴音频
                startActivityForResult(Intent(this,PianoActivity::class.java),LocalWork.PINAO)
                popLocalMenu?.dismiss()
            }
            R.id.bt_cancel -> {//取消删除
                setShow(false)
            }
            R.id.bt_all -> {//全选按钮
                if (selectedWorks.size >= works.size) {
                    for (i in works.indices) {
                        works[i].isChecked = false
                    }
                    selectedWorks.clear()
                } else {
                    for (i in works.indices) {
                        works[i].isChecked = true
                    }
                    selectedWorks.clear()
                    selectedWorks.addAll(works)
                }
                changeCheckAllButton()
                adapter?.notifyDataSetChanged()
            }
            R.id.bt_del -> {//删除按钮
                if (selectedWorks.size != 0) {
                    deleteDialog()
                }
            }
        }
    }

    //改变全选按钮状态
    private fun changeCheckAllButton() {
        if (selectedWorks.size >= works.size) {
            bt_all.text = "全不选"
        } else {
            bt_all.text = "全选"
        }
        if (selectedWorks.size <= 0) {
            bt_del.isEnabled = false
        } else
            bt_del.isEnabled = true
    }

    private fun setShow(isShow: Boolean) {
        this.isShow = isShow
        if (isShow) {
            ObjectAnimator.ofFloat(ll_head, "y", -ll_head.height.toFloat(), 0f).setDuration(300).start()
            ll_head.visibility = View.VISIBLE
            rl_bottom.visibility = View.VISIBLE
            ObjectAnimator.ofFloat(rl_bottom, "translationY", (DensityUtil.dip2px(this, 55f) - 2).toFloat(), 0f).setDuration(300)
                .start()
        } else {
            val animator = ObjectAnimator.ofFloat(ll_head, "translationY", 0f, -ll_head.height.toFloat())
            animator.setDuration(300).start()
            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    ll_head.visibility = View.GONE
                }
            })
            rl_bottom.visibility = View.GONE
            for (i in works.indices) {
                works[i].isChecked = false
            }
            selectedWorks.clear()
        }
    }

    /**
     * 删除
     */
    private fun deleteDialog() {
        AlertDialog.Builder(this).setTitle("是否删除已选择作品?")
            .setNegativeButton("否",null)
            .setPositiveButton("是") { _, _ ->
                for (i in works.indices) {
                    if (works[i].isChecked) {
                        val strdelte = "delete from contact where _id=" + works[i].id
                        val file = File(works[i].path)
                        db?.execSQL(strdelte)
                        if (file.exists()) {
                            val path = file.absolutePath
                            file.delete()
                            MediaScannerConnection.scanFile(
                                this@LocalVideoListActivity,
                                arrayOf(path),
                                arrayOf("video/mp4")
                            ) { path, uri -> Log.i("TAG", "onScanCompleted$path") }
                        }
                    }
                }
                works.removeAll(selectedWorks)

                requestData()
                ArmsUtils.makeText(application,"操作成功")
                setShow(false)
            }
            .show()
    }

    @Synchronized
    protected fun requestData() {//从本地数据库查询获得全部
        Thread(Runnable {
            works.clear()
            var strselect = ""
            if (!isShare)
            //全部查询
                strselect = "select * from contact"
            else
            //只查询视频
                strselect = "select * from contact WHERE type= 2"
            val cursor = db?.rawQuery(strselect, null)
            val localWorks = ArrayList<LocalWork>()
            while (cursor?.moveToNext() == true) {

                val localWork = LocalWork()
                localWork.id = cursor.getString(cursor.getColumnIndexOrThrow("_id"))
                localWork.type = cursor.getInt(cursor.getColumnIndexOrThrow("type"))
                localWork.name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                localWork.path = cursor.getString(cursor.getColumnIndexOrThrow("path"))
                localWork.shareId = cursor.getString(cursor.getColumnIndexOrThrow("shareId"))
                localWork.scoreId = cursor.getString(cursor.getColumnIndexOrThrow("scoreId"))
                localWork.totalScore = cursor.getInt(cursor.getColumnIndexOrThrow("totalScore"))
                localWork.scoreName = cursor.getString(cursor.getColumnIndexOrThrow("scoreName"))
                localWork.createtime = cursor.getLong(cursor.getColumnIndexOrThrow("createtime"))
                localWorks.add(localWork)
            }
            cursor?.close()
            if (localWorks == null || localWorks.size == 0) {
                tv_nodata.post(Runnable { tv_nodata.setVisibility(View.VISIBLE) })

            } else {
                tv_nodata.post(Runnable { tv_nodata.setVisibility(View.GONE) })
            }
            runOnUiThread {
                adapter?.addAll(localWorks)
                adapter?.notifyDataSetChanged()
                LogUtils.warnInfo(localWorks.toString())
            }
        }).start()

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
        intent.putExtra("videoType", 0)
        startActivityForResult(intent, LocalWork.VIDEO)
    }

    private fun createCaptureConfiguration(): CaptureConfiguration {
        //分辨率
        val resolution =when(videoPixel){
            1 -> CaptureResolution.RES_480P
            2 -> CaptureResolution.RES_720P
            3 -> CaptureResolution.RES_1080P
            else -> CaptureResolution.RES_480P
        }
        //质量
        val quality = CaptureQuality.LOW //低质量
        //限制时长
        val fileDuration = videoTime //10分钟
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
                showDialog(path)
            }
        }
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
                //                    String refreshFile = newPath.substring(0,newPath.indexOf(name+".mp4"));
                MediaScannerConnection.scanFile(
                    this@LocalVideoListActivity,
                    arrayOf(newPath, path),
                    arrayOf("video/mp4", "video/mp4")
                ) { path, uri -> Log.i("TAG", "onScanCompleted$path") }

                //往本地数据库里增加一条
                val strinsert =
                    " insert into contact (type,name,path,createtime) values('" + 1 + "','" +
                            name + "','" + newPath + "','" + System.currentTimeMillis() + "')"
                db!!.execSQL(strinsert)
                requestData()
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

    override fun onResume() {
        super.onResume()
        requestData()
    }

    override fun onPause() {
        super.onPause()
        setShow(false)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isShow) {
                setShow(false)
            } else
                finish()
            return true
        }
        return false
    }

}