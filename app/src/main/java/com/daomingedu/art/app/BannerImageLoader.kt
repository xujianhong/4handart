package com.daomingedu.art.app

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.youth.banner.loader.ImageLoader

class BannerImageLoader: ImageLoader() {
  override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
    Glide.with(context!!)
      .load(path)
      .into(imageView!!)
  }

}