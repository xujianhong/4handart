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

import com.daomingedu.art.mvp.model.entity.BaseJson
import com.daomingedu.art.mvp.model.entity.RegisterBean
import com.daomingedu.art.mvp.model.entity.UserInfoBean
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * ================================================
 * 展示 [Retrofit.create] 中需要传入的 ApiService 的使用方式
 * 存放关于用户的一些 API
 *
 *
 * Created by JessYan on 08/05/2016 12:05
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
interface CustomerService {
    /*String HEADER_API_VERSION = "Accept: application/vnd.github.v3+json";

    @Headers({HEADER_API_VERSION})
    @GET("/users")
    Observable<List<Object>> getUsers(@Query("since") int lastIdQueried, @Query("per_page") int perPage);*/
    /**
     * 注册
     *
     * @param key
     * @param mobile 登录手机号	true
     * @param password  登录密码	true
     * @param channel   登录渠道,1:Android,2:Ios	true
     * @param pushId    极光推送regId	false
     * @param deviceNo  手机设备号	false
     * @param versionCode   登录APP版本号	true
     * @param code  短信验证码	true
     * @return
     */
    @FormUrlEncoded
    @POST("api/customer/reg.do")
    fun reg(
        @Field("key") key: String, @Field("mobile") mobile: String,
        @Field("password") password: String, @Field("channel") channel: Int, @Field("pushId") pushId: String?,
        @Field("deviceNo") deviceNo: String?, @Field("versionCode") versionCode: Int, @Field("code") code: String
    ): Observable<BaseJson<RegisterBean>>

    /**
     * 登录
     *
     * @param mobile 登录手机号	true
     * @param password  登录密码	true
     * @param channel   登录渠道,1:Android,2:Ios	true
     * @param pushId    极光推送regId	false
     * @param deviceNo  手机设备号	false
     * @param versionCode   登录APP版本号	true
     * @return
     */
    @FormUrlEncoded
    @POST("api/customer/login")
    fun login(
        @Field("key") key: String, @Field("mobile") mobile: String,
        @Field("password") password: String, @Field("channel") channel: Int, @Field("pushId") pushId: String?,
        @Field("deviceNo") deviceNo: String?, @Field("versionCode") versionCode: Int
    ): Observable<BaseJson<RegisterBean>>

    /**
     * 注销登陆
     *
     * @param sessionId 本次会话Id,时效24小时
     * @return
     */
    @FormUrlEncoded
    @POST("api/customer/logout")
    fun logout(
        @Field("sessionId") sessionId: String
    ): Observable<BaseJson<Any>>

    /**
     * 找回密码
     *
     * @param key   key
     * @param mobile    手机号
     * @param code  短信验证码
     * @param newPsw    新密码
     * @return
     */
    @FormUrlEncoded
    @POST("api/customer/getPsw")
    fun getPsw(
        @Field("key") key: String, @Field("mobile") mobile: String,
        @Field("code") code: String, @Field("newPsw") newPsw: String
    ): Observable<BaseJson<Any>>

    /**
     * 修改密码
     *
     * @param sessionId 本次会话Id,时效24小时
     * @param oldPsw    旧密码
     * @param newPsw    新密码
     * @return
     */
    @FormUrlEncoded
    @POST("api/customer/changePsw")
    fun changePsw(
        @Field("sessionId") sessionId: String, @Field("oldPsw") oldPsw: String,
        @Field("newPsw") newPsw: String
    ): Observable<BaseJson<Any>>

    /**
     * 获取用户信息
     *
     * @param sessionId 本次会话Id,时效24小时
     * @return
     */
    @FormUrlEncoded
    @POST("api/customer/getCustomerInfo")
    fun getCustomerInfo(
        @Field("sessionId") sessionId: String
    ): Observable<BaseJson<UserInfoBean>>

    /**
     * 编辑个人资料
     *
     * @param sessionId 本次会话Id,时效24小时
     * @param image 头像的base64编码，不能大于40kb
     * @param realName  真实姓名
     * @param nickName  昵称
     * @param sex   性别,1男0女
     * @param birthday  生日，格式：2000-05-05
     * @param motto 学习宣言
     * @return
     */
    @FormUrlEncoded
    @POST("api/customer/updateCustomer")
    fun updateCustomer(
        @Field("sessionId") sessionId: String,@Field("image") image:String?,
        @Field("realName") realName:String?,@Field("nickName") nickName:String?,
        @Field("sex") sex:Int?,@Field("birthday") birthday:String?,@Field("motto") motto:String?
    ): Observable<BaseJson<Any>>
}
