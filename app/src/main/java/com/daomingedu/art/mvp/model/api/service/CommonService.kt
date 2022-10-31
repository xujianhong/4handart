/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.daomingedu.art.mvp.model.api.service

import com.daomingedu.art.app.UploadFileRequestBody
import com.daomingedu.art.mvp.model.entity.*
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * ================================================
 * 存放通用的一些 API
 *
 *
 * Created by JessYan on 08/05/2016 12:05
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
interface CommonService {
    /**
     * 短信发送接口
     *
     * @param key
     * @param mobile    手机号	true
     * @param type  1:注册,2：找回密码	true
     * @return
     */
    @FormUrlEncoded
    @POST("api/common/sendSMS")
    fun sendSMS(
        @Field("key") key: String, @Field("mobile") mobile: String,
        @Field("type") type: Int
    ): Observable<BaseJson<Any>>

    /**
     * 检查sessionId是否有效
     *
     * @param sessionId 本次会话Id,时效24小时
     * @return
     */
    @FormUrlEncoded
    @POST("api/common/checkSessionId")
    fun checkSessionId(
        @Field("sessionId") sessionId: String
    ): Observable<BaseJson<SessionIdBean>>

    /**
     * 考级城市列表
     *
     * @param sessionId 本次会话Id,时效24小时
     * @param type  考级类型，1：音乐考级 ，2：舞蹈考级
     * @return
     */
    @FormUrlEncoded
    @POST("api/common/cityList")
    fun cityList(
        @Field("sessionId") sessionId: String, @Field("type") type: Int
    ): Observable<BaseJson<MutableList<TestCityBean>>>

    /**
     * 关于我们
     *
     * @param sessionId 本次会话Id,时效24小时
     * @return
     */
    @FormUrlEncoded
    @POST("api/common/aboutUs")
    fun aboutUs(
        @Field("sessionId") sessionId: String
    ): Observable<BaseJson<AboutUsBean>>

    /**
     * 文件上传
     *
     * @param options
     * @param externalFileParameters
     * @return
     */
    @Multipart
    @POST("api/common/uploadFile")
    fun uploadFile(
        @QueryMap options: Map<String, String>,
        @PartMap externalFileParameters: Map<String, UploadFileRequestBody>
    ): Observable<BaseJson<UploadInfoBean>>

    /**
     * 最新版本信息
     *
     * @param key   key=bfbcd9d2974b4d33bca9012b4b2b28c5
     * @param systemType    1:android 2:IOS
     * @return
     */
    @FormUrlEncoded
    @POST("api/common/getVersionInfo")
    fun getVersionInfo(
        @Field("key") key: String,
        @Field("systemType") systemType: Int
    ): Observable<BaseJson<VersionBean>>

    /**
     * 考级列表
     *
     * @param key
     * @return
     */
    @FormUrlEncoded
    @POST("api/common/gradedList")
    fun gradedList(
        @Field("key") key: String
    ): Observable<BaseJson<MutableList<GradeBean>>>

    /**
     * 学习圈举报/屏蔽
     *
     * @param sessionId
     * @param shareId   被举报的shareId (shareId和userId不能同时为空）
     * @param userId    被屏蔽的userId  (shareId和userId不能同时为空）
     * @param type  举报类型(1:涉黄毒赌2政治敏感3宣传邪教4广告
     * @param remake    举报描述
     * @return
     */
    @FormUrlEncoded
    @POST("api/common/reportShare")
    fun reportShare(
        @Field("sessionId") sessionId: String,
        @Field("shareId") shareId: String?,
        @Field("userId") userId: String?,
        @Field("type") type: Int,
        @Field("remake") remake: String?
    ): Observable<BaseJson<Any>>

    /**
     * 我屏蔽的用户列表
     *
     * @param sessionId
     * @param start 开始行号
     * @param size  每页行数
     * @return
     */
    @FormUrlEncoded
    @POST("api/common/myReport")
    fun myReport(
        @Field("sessionId") sessionId: String,
        @Field("start") start: Int,
        @Field("size") size: Int
    ): Observable<BaseJson<MutableList<BlockUserBean>>>

    /**
     * 取消屏蔽用户
     *
     * @param sessionId
     * @param reportId     举报id
     * @return
     */
    @FormUrlEncoded
    @POST("api/common/delReport")
    fun delReport(
        @Field("sessionId") sessionId: String,
        @Field("reportId") reportId: String
    ): Observable<BaseJson<Any>>

    @FormUrlEncoded
    @POST("api/customer/cancelAccount")
    fun cancelAccount(
        @Field("sessionId") sessionId: String
    ): Observable<BaseJson<Any>>
}
