package com.daomingedu.art.mvp.ui.activity

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.media.ThumbnailUtils
import android.os.Bundle
import android.provider.MediaStore

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import com.daomingedu.art.R
import com.daomingedu.art.app.onClick
import com.daomingedu.art.app.visible
import com.daomingedu.art.di.component.DaggerUploadShareComponent
import com.daomingedu.art.di.module.UploadShareModule
import com.daomingedu.art.mvp.contract.UploadShareContract
import com.daomingedu.art.mvp.model.LocalWork
import com.daomingedu.art.mvp.presenter.UploadSharePresenter
import com.daomingedu.art.mvp.ui.adapter.UploadPhotosAdapter
import com.daomingedu.art.mvp.ui.widget.LoadingDialog2
import com.daomingedu.art.mvp.ui.widget.selectimagevideo.SelectImageVideoView
import com.daomingedu.art.util.startActivityForResult
import com.google.gson.Gson
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.LogUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_upload_share.*
import kotlinx.android.synthetic.main.include_page_head.*
import me.jessyan.autosize.AutoSize
import me.jessyan.autosize.internal.CancelAdapt
import org.jetbrains.anko.startActivity
import java.io.File
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/07/2019 19:50
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">?????????????????????</a>
 * ================================================
 */
/**
 * ?????????presenter
 * ??????????????????
 *
 * @ActivityScope(?????????????????????) class NullObjectPresenterByActivity
 * @Inject constructor() : IPresenter {
 * override fun onStart() {
 * }
 *
 * override fun onDestroy() {
 * }
 * }
 */
class UploadShareActivity : BaseActivity<UploadSharePresenter>(), UploadShareContract.View,CancelAdapt {
    override fun requestCreateShareSuccess(data: String?) {
        ArmsUtils.makeText(application,"????????????")
        killMyself()
    }

    @Inject
    lateinit var mAdapter:UploadPhotosAdapter
    @Inject
    lateinit var pathList:MutableList<String>
    private var videoFile: File? = null
    private var thumbnailBitmap: Bitmap? = null

    val type by lazy { intent.getIntExtra(TYPE,0) }
    val loadingDialog: LoadingDialog2 by lazy {
        LoadingDialog2(this,tips ="?????????")
    }
    val threadPoolExecutor by lazy {
        ThreadPoolExecutor(3, 5,
            1, TimeUnit.SECONDS, LinkedBlockingDeque<Runnable>(128)
        )
    }
    companion object{
        val TYPE = "type"
        val TYPE_PHOTO = 1//????????????
        val TYPE_VIDEO = 3//????????????
        val TYPE_RECORDING = 2//????????????
        val PHOTOS_NUM = 6//??????????????????
        val ADD_PHOTO_STR = "addButton"
        val PICK_PHOTO = 0x00
        val ADD_PHOTO = 0x01
        val SCAN_PHOTOS = 0x02
        val PICK_VIDEO = 0x03
        val PICK_RECORDING = 0x04
    }
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerUploadShareComponent //??????????????????,?????????????????????
            .builder()
            .appComponent(appComponent)
            .uploadShareModule(UploadShareModule(this))
            .build()
            .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_upload_share //???????????????????????????????????? setContentView(id) ??????????????????,????????? 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        tv_name.text = "??????"
        val toolbar = findViewById<Toolbar>(R.id.toolbar2)
        toolbar.background.alpha = 255
        toolbar.setNavigationIcon(R.drawable.ic_back_white)
        toolbar.setNavigationOnClickListener { killMyself() }
        toolbar.inflateMenu(R.menu.menu_upload_sure)
        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.add){//??????
                when (pathList.isEmpty()){
                    true -> {
                        Toast.makeText(this, "???????????????????????????", Toast.LENGTH_LONG).show()
                        return@setOnMenuItemClickListener false
                    }
                }
                when(type) {
                    TYPE_PHOTO -> {
                        val content = et_remark.text.toString().trim()
                        mPresenter?.uploadFile(pathList,TYPE_PHOTO,content)
                    }
                    TYPE_VIDEO -> {
                        val content = et_remark.text.toString().trim()
                        mPresenter?.uploadFile(pathList,TYPE_VIDEO,content)
                    }
                    TYPE_RECORDING -> {
                        val content = et_remark.text.toString().trim()
                        mPresenter?.uploadFile(pathList, TYPE_RECORDING,content)
                    }
                }
            }
            return@setOnMenuItemClickListener false
        }
        when(type) {
            TYPE_PHOTO -> {
                btn_pick.text = "????????????"
                rv_photos.visible(true)
            }
            TYPE_VIDEO -> {
                btn_pick.text = "????????????"
                rv_photos.visible(false)
            }
            TYPE_RECORDING -> {
                btn_pick.text = "????????????"
                rv_photos.visible(false)
            }
        }
        btn_pick.onClick {
            RxPermissions(this).request(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe {
                    if (it){
                        if (type == TYPE_PHOTO) {
                            startActivityForResult<SelectImageVideoActivity>(
                                SelectImageVideoActivity.KEY_SELECT_NUM to PHOTOS_NUM,
                                SelectImageVideoActivity.KEY_CAMERA_SHOW to true,
                                SelectImageVideoActivity.KEY_TYPE to SelectImageVideoView.TYPE_IMAGE,
                                callback = {
                                    val result = it.getStringArrayListExtra(SelectImageVideoActivity.KEY_RESULT)
                                    if (result == null || result.size == 0) {
                                        ArmsUtils.makeText(application,"??????????????????")
                                        return@startActivityForResult
                                    }
                                    LogUtils.debugInfo("???????????????" + result.toString())
                                    pathList.clear()
                                    pathList.addAll(result)
                                    if (result.size < 6) {
                                        pathList.add(ADD_PHOTO_STR)
                                    }
                                    mAdapter.notifyDataSetChanged()
                                }
                            )
                        } else if (type == TYPE_VIDEO) {
                            startActivityForResult<SelectImageVideoActivity>(
                                SelectImageVideoActivity.KEY_SELECT_NUM to 1,
                                SelectImageVideoActivity.KEY_CAMERA_SHOW to false,
                                SelectImageVideoActivity.KEY_TYPE to SelectImageVideoView.TYPE_VIDEO,
                                callback = {
                                    val videoPath = it.getStringArrayListExtra(SelectImageVideoActivity.KEY_RESULT)
                                    if (videoPath == null || videoPath.size == 0) {
                                        ArmsUtils.makeText(application,"??????????????????")
                                        return@startActivityForResult
                                    }
                                    getVideo(videoPath[0])
                                }
                            )
                        } else if (type == TYPE_RECORDING) {
                            /*Bundle bundle = new Bundle();
                            bundle.putBoolean("isShare", true);
                            bd.jumpResult(LocalWorkAct.class, PICK_RECORDING, bundle);*/
                            startActivityForResult<LocalWorkActivity>("isShare" to true, callback = {
                                val work = it.getExtras()!!.getSerializable("work") as LocalWork
                                getRecording(work!!.getPath())
                            })
                        }
                    }else{
                        ArmsUtils.makeText(application,"??????????????????")
                    }
                }

        }
        rv_photos.layoutManager = GridLayoutManager(this, 4)
        rv_photos.adapter = mAdapter.apply {
            setOnItemClickListener { adapter, view, position ->
                if (ADD_PHOTO_STR.equals(adapter.getData().get(position))) {
//                    pickPhoto(7 - pathList.size, ADD_PHOTO)
                    val pickCount = 7 - pathList.size
                    startActivityForResult<SelectImageVideoActivity>(
                        SelectImageVideoActivity.KEY_SELECT_NUM to pickCount,
                        SelectImageVideoActivity.KEY_CAMERA_SHOW to true,
                        SelectImageVideoActivity.KEY_TYPE to SelectImageVideoView.TYPE_IMAGE,
                        callback = {
                            val result = it.getStringArrayListExtra(SelectImageVideoActivity.KEY_RESULT)
                            if (result == null || result!!.size == 0) {
                                ArmsUtils.makeText(application,"??????????????????")
                                return@startActivityForResult
                            }
                            mAdapter.addData(pathList.size - 1, result)
                            if (pathList.size >= 7) {
                                pathList.removeAt(6)
                            }
                            mAdapter.notifyDataSetChanged()
                        }
                    )
                } else {
                    startActivityForResult<ImageDetailsActivity>("position" to position,
                       "images" to mAdapter.data as Serializable,
                        "canDel" to true,callback = {
                            val images = it.getExtras()!!.getSerializable("images") as List<String>
                            pathList.clear()
                            pathList.addAll(images)
                            if (images.size < 6) {
                                pathList.add(ADD_PHOTO_STR)
                            }
                            mAdapter.notifyDataSetChanged()
                        })
                }
            }
        }
        iv_video_preview.setOnClickListener {
            startActivity<UploadVideoPreviewActivity>("filePath" to videoFile?.getAbsolutePath())
        }
    }


    override fun showLoading() {
        loadingDialog.show()
    }

    override fun hideLoading() {
        loadingDialog.dismiss()
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

    private fun getVideo(videoPath: String) {
        pathList.clear()
        pathList.add(videoPath)
        videoFile = File(videoPath)
        /*if (videoFile?.length() > (application as MyApp).getVideo()) {
            bd.toastError("????????????+" + ((application as MyApp).getVideo() / (1024 * 1024)) as Int + "M?????????????????????")
            return
        }*/
        thumbnailBitmap =
            ThumbnailUtils.createVideoThumbnail(videoFile?.path, MediaStore.Images.Thumbnails.MINI_KIND)
        //                bitmap = ThumbnailUtils.extractThumbnail(bitmap, 200, 100);
        rl_video_preview.visibility = View.VISIBLE
        iv_video_preview.setImageBitmap(thumbnailBitmap)
        val mmr = MediaMetadataRetriever()
        mmr.setDataSource(videoFile?.path)
        val duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        val videoLength = SimpleDateFormat("mm:ss").format(java.lang.Long.parseLong(duration))
        tv_video_length.text = videoLength
    }

    private fun getRecording(path: String) {
        pathList.add(path)
        videoFile = File(path)
        /*if (videoFile?.length() > (application as MyApp).getRecord()) {
            bd.toastError("????????????+" + ((application as MyApp).getRecord() / (1024 * 1024)) as Int + "M?????????????????????")
            return
        }*/

        //                bitmap = ThumbnailUtils.extractThumbnail(bitmap, 200, 100);
        rl_recording_preview.visibility = View.VISIBLE
        val mmr = MediaMetadataRetriever()
        mmr.setDataSource(videoFile?.getPath())
        val duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        val videoLength = SimpleDateFormat("mm:ss").format(java.lang.Long.parseLong(duration))
        tv_recording_length.text = videoLength

        rl_recording_preview.setOnClickListener {
            startActivity<UploadVideoPreviewActivity>("filePath" to videoFile?.getAbsolutePath(),
                "audiofile" to 1)
        }
    }

    override fun onResume() {
        super.onResume()
        AutoSize.cancelAdapt(this)
    }
}
