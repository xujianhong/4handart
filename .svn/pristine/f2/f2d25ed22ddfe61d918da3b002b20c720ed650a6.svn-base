package com.daomingedu.art.mvp.model.api.service

import com.daomingedu.art.mvp.model.entity.BannerBean
import com.daomingedu.art.mvp.model.entity.BaseJson
import com.daomingedu.art.mvp.model.entity.NewsBean
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface NewsService {
    /**
     * banner列表
     *
     * @param sessionId 本次会话Id,时效24小时
     * @return
     */
    @FormUrlEncoded
    @POST("api/news/bannerList")
    fun bannerList(
        @Field("sessionId") sessionId: String
    ): Observable<BaseJson<MutableList<BannerBean>>>

    /**
     * 新闻列表
     *
     * @param sessionId 本次会话Id,时效24小时
     * @param type  咨询类型，1：通用
     * @param start 列表第一条数据位置
     * @param size  列表数据数量
     * @return
     */
    @FormUrlEncoded
    @POST("api/news/newsList")
    fun newsList(
        @Field("sessionId") sessionId: String,
        @Field("type") type:Int,
        @Field("start") start:Int,
        @Field("size") size:Int
    ): Observable<BaseJson<MutableList<NewsBean>>>
}