package com.daomingedu.art.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.daomingedu.art.R
import com.daomingedu.art.mvp.model.entity.TestCityBean

class MusicDanceTestAdapter(datas: MutableList<TestCityBean>) :
    BaseQuickAdapter<TestCityBean,BaseViewHolder>(R.layout.item_music_dance_test,datas){
    override fun convert(helper: BaseViewHolder, item: TestCityBean) {
        helper.setText(R.id.tvTitle,item.cityName)
        helper.setText(R.id.tvSubTitle,item.remark)
    }

}