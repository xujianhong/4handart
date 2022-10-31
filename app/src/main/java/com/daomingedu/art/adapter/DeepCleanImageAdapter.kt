package com.daomingedu.art.adapter

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.daomingedu.art.R
import com.daomingedu.art.bean.FileBean
import com.google.gson.Gson
import kotlinx.android.synthetic.main.item_deepcleanimage.view.*
import java.io.File
import java.text.DecimalFormat

class DeepCleanImageAdapter(val context: Context, val list: ArrayList<File>, val deep_clean_clean: TextView, val deep_clean_cancel: TextView, val deep_clean_ll: LinearLayout): RecyclerView.Adapter<DeepCleanImageAdapter.MyViewHolder>() {

    var isDelete = false
    var totalsize : Long = 0
    var list_file = ArrayList<FileBean>()

    init {
        for (item in list.indices){
            list_file.add(FileBean(list[item], false))

        }
        deep_clean_cancel.setOnClickListener {
            deep_clean_ll.visibility = View.GONE
            isDelete = false
            totalsize = 0
            for (item in list_file){
                item.isCheck = false
            }
            deep_clean_clean.text = "清理"
            notifyItemRangeChanged(0, list.size)
        }
        deep_clean_clean.setOnClickListener {
            for (i in list_file.size-1 downTo 0){
                val item = list_file[i]
                when(item.isCheck){
                    true -> {
                        when(item.file.delete()){
                            true -> {
                                list_file.removeAt(i)
                                notifyItemRemoved(i)
                                notifyItemRangeChanged(0, list_file.size)
                            }
                        }
                    }
                }
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_deepcleanimage, parent, false))
    }

    override fun getItemCount(): Int {
        return list_file.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(context, list_file[position], list_file, this, holder.adapterPosition, deep_clean_ll, deep_clean_clean)
    }

    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view){

        private fun setSize(size: Long): String{
            val GB = 1024 * 1024 * 1024
            val MB = 1024 * 1024
            val KB = 1024
            val df = DecimalFormat("0")
            var resultSize = ""
            if(size / GB >= 1){
                resultSize = df.format(size / GB) + "GB"
            }else if (size / MB >= 1){
                resultSize = df.format(size / MB) + "MB"
            }else if (size / KB >= 1){
                resultSize = df.format(size / KB) + "KB"
            }else{
                resultSize = "$size B"
            }
            return resultSize
        }

        fun bindData(context: Context, fileBean: FileBean, list: ArrayList<FileBean>, deepCleanImageAdapter: DeepCleanImageAdapter, position: Int, deep_clean_ll: LinearLayout, deep_clean_clean: TextView){
            when{
                fileBean.file.name.endsWith(".mp4") -> Glide.with(context).load(Uri.fromFile(fileBean.file)).into(
                        (view.item_deepcleanimage_iv)
                    )
            }
            view.item_deepcleanimage_tv.text = setSize(fileBean.file.length())
            view.setOnClickListener {
                Log.d("test", "isdelete="+isDelete)
                when(isDelete){
                    true -> {
                        when(list_file[position].isCheck){
                            true -> {
                                list_file[position].isCheck = false
                                val size = fileBean.file.length()
                                totalsize -= size
                                deep_clean_clean.text = "清理"+ setSize(totalsize)
                            }
                            false -> {
                                val size = fileBean.file.length()
                                totalsize += size
                                deep_clean_clean.text = "清理"+ setSize(totalsize)
                                list_file[position].isCheck = true
                            }
                        }
                        notifyItemChanged(position)

                    }
                    false -> {
                        when{
                            fileBean.file.name.endsWith(".mp4") -> {
                                val intent = Intent()
                                intent.putExtra("result",fileBean.file.absolutePath)
                                (context as AppCompatActivity).setResult(Activity.RESULT_OK, intent)
                                context.finish()
                            }
                        }
                    }
                }
            }
            when(list_file[position].isCheck){
                true -> {
                    view.item_deepcleanimage_status.setImageResource(R.drawable.rb_on)
                }
                false -> {
                    view.item_deepcleanimage_status.setImageResource(R.drawable.rb_off_white)
                }
            }
            when(isDelete){
                true -> view.item_deepcleanimage_status.visibility = View.VISIBLE
                false -> view.item_deepcleanimage_status.visibility = View.GONE
            }

            view.setOnLongClickListener(object : View.OnLongClickListener{
                override fun onLongClick(v: View?): Boolean {
                    when(isDelete){
                        false -> {
                            deep_clean_ll.visibility = View.VISIBLE
                            isDelete = true
                            notifyItemRangeChanged(0, list.size)
                        }
                    }

                    /*val dialog = AlertDialog.Builder(context)
                    when{
                        file.name.endsWith(".jpg") -> dialog.setMessage("确定删除图片?")
                        file.name.endsWith(".mp4") -> dialog.setMessage("确定删除视频?")
                    }
                    dialog.setPositiveButton("删除", object : DialogInterface.OnClickListener{
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                                when(file.delete()){
                                    true -> Toast.makeText(context, "删除成功", Toast.LENGTH_LONG).show()
                                    false -> Toast.makeText(context, "删除失败", Toast.LENGTH_LONG).show()
                                }
                                dialog?.dismiss()
                                list.removeAt(position)
                                deepCleanImageAdapter.notifyItemRemoved(position)
                        }
                    })
                    dialog.setNegativeButton("取消", object : DialogInterface.OnClickListener{
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            dialog?.dismiss()
                        }
                    })
                    dialog.create().show()*/

                    return true
                }
            })
        }
    }
}