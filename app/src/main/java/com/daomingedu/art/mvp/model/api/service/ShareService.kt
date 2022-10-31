package com.daomingedu.art.mvp.model.api.service

import com.daomingedu.art.mvp.model.entity.BaseJson
import com.daomingedu.art.mvp.model.entity.ShareBean
import com.daomingedu.art.mvp.model.entity.ShareDetailBean
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ShareService {
    /**
     * 创建分享
     *
     * @param sessionId 本次会话Id,时效24小时
     * @param shareType 1图片 2录音 3视频
     * @param remark    分享描述
     * @param previewImage  如果是视频，视频首帧图片path
     * @param filePath  如果是音频或者视频，文件path
     * @param imageUrls 如果是图片，图片的url，多个以逗号间隔
     * @param imageSmallUrls    如果是图片，略缩图片的urls，多个以逗号间隔
     * @return
     */
    @FormUrlEncoded
    @POST("api/share/createShare")
    fun createShare(
        @Field("sessionId") sessionId: String,
        @Field("shareType") shareType: Int,
        @Field("remark") remark: String?=null,
        @Field("previewImage") previewImage: String?=null,
        @Field("filePath") filePath: String?=null,
        @Field("imageUrls") imageUrls: String?=null,
        @Field("imageSmallUrls") imageSmallUrls: String?=null
    ): Observable<BaseJson<String>>

    /**
     * 分享列表
     *
     * @param sessionId 本次会话Id,时效24小时
     * @param isMy  此处传1获取我的分享列表，传0获取全部分享列表
     * @param start 开始行号
     * @param size  每页行数
     * @return
     */
    @FormUrlEncoded
    @POST("api/share/getShareList")
    fun getShareList(
        @Field("sessionId") sessionId: String,
        @Field("isMy") isMy: Int,
        @Field("start") start: Int,
        @Field("size") size: Int
    ): Observable<BaseJson<MutableList<ShareBean>>>


    /**
     * 删除分享
     *
     * @param sessionId 本次会话Id,时效24小时
     * @param shareId   shareId
     * @return
     */
    @FormUrlEncoded
    @POST("api/share/delShare")
    fun delShare(
        @Field("sessionId") sessionId: String,
        @Field("shareId") shareId: String
    ): Observable<BaseJson<Any>>

    /**
     * 分享详情
     *
     * @param sessionId 本次会话Id,时效24小时
     * @param shareId   shareId
     * @return
     */
    @FormUrlEncoded
    @POST("api/share/shareDetail")
    fun shareDetail(
        @Field("sessionId") sessionId: String,
        @Field("shareId") shareId: String
    ): Observable<BaseJson<ShareDetailBean>>

    /**
     * 提交评论
     *
     * @param sessionId 本次会话Id,时效24小时
     * @param shareId   shareId
     * @param comment   评论内容
     * @return
     */
    @FormUrlEncoded
    @POST("api/share/shareComment")
    fun shareComment(
        @Field("sessionId") sessionId: String,
        @Field("shareId") shareId: String,
        @Field("comment") comment: String
    ): Observable<BaseJson<Any>>

    /**
     * 点赞分享(再次点赞为取消点赞)
     *
     * @param sessionId 本次会话Id,时效24小时
     * @param shareId   shareId
     * @return
     */
    @FormUrlEncoded
    @POST("api/share/shareLike")
    fun shareLike(
        @Field("sessionId") sessionId: String,
        @Field("shareId") shareId: String
    ): Observable<BaseJson<Any>>
}