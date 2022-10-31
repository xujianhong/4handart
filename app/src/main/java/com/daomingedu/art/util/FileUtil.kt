package com.daomingedu.art.util

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

object FileUtil {

    fun getRealFilePath(context: Context, uri: Uri): String{
        val scheme = uri.scheme
        when(scheme == null){
            true -> return uri.path!!
            false -> {
                when(scheme){
                    ContentResolver.SCHEME_FILE -> {
                        return uri.path!!
                    }
                    ContentResolver.SCHEME_CONTENT -> {
                        val cursor = context.contentResolver.query(
                            uri,
                            arrayOf(MediaStore.Images.ImageColumns.DATA),
                            null,
                            null,
                            null)
                        if (cursor != null){
                            if (cursor.moveToFirst()){
                                val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                                if (index > -1){
                                    return cursor.getString(index)
                                }
                            }
                            cursor.close()
                        }
                    }
                }
            }
        }
        return ""
    }

    /**
     * @param bmp 获取的bitmap数据
     * @param picName 自定义的图片名
     */
    fun saveBmp2Gallery(context: Context, bmp: Bitmap, picName: String) {
        saveImageToGallery(bmp, picName)
        var fileName: String? = null
        //系统相册目录
        val galleryPath = (Environment.getExternalStorageDirectory().toString()
                + File.separator + Environment.DIRECTORY_DCIM
                + File.separator + "Camera" + File.separator)


        // 声明文件对象
        var file: File? = null
        // 声明输出流
        var outStream: FileOutputStream? = null
        try {
            // 如果有目标文件，直接获得文件对象，否则创建一个以filename为名称的文件
            file = File(galleryPath, "$picName.jpg")
            // 获得文件相对路径
            fileName = file.toString()
            // 获得输出流，如果文件中有内容，追加内容
            outStream = FileOutputStream(fileName)
            if (null != outStream) {
                bmp.compress(Bitmap.CompressFormat.JPEG, 90, outStream)
            }
        } catch (e: Exception) {
            e.stackTrace
        } finally {
            try {
                outStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        MediaStore.Images.Media.insertImage(context.contentResolver, bmp, fileName, null)
        val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val uri = Uri.fromFile(file)
        intent.data = uri
        context.sendBroadcast(intent)

        Toast.makeText(context, "图片保存成功", Toast.LENGTH_SHORT).show()
    }

    /**
     * 保存图片到图库
     * @param bmp
     */
    fun saveImageToGallery(bmp: Bitmap, bitName: String) {
        // 首先保存图片
        val appDir = File(
            Environment.getExternalStorageDirectory(),
            "yingtan"
        )
        if (!appDir.exists()) {
            appDir.mkdir()
        }

        val fileName = "$bitName.jpg"
        val file = File(appDir, fileName)

        try {
            val fos = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}