package com.daomingedu.art.mvp.model.api.service

import com.daomingedu.art.mvp.model.entity.BannerBean
import com.daomingedu.art.mvp.model.entity.BaseJson
import com.daomingedu.art.mvp.model.entity.CheckCerBean
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface CerStuService {
    /**
     * 检查认证状态
     *
     * @param sessionId 本次会话Id,时效24小时
     * @return
     */
    @FormUrlEncoded
    @POST("api/cerStu/checkCer")
    fun checkCer(
        @Field("sessionId") sessionId: String
    ): Observable<BaseJson<CheckCerBean>>

    /**
     * 保存实名认证(state =1时不用提交认证)
     *
     * @param sessionId 本次会话Id,时效24小时
     * @param name  姓名
     * @param idNumber  身份证号码
     * @param phonePath 寸照路径
     * @return
     */
    @FormUrlEncoded
    @POST("api/cerStu/saveCer")
    fun saveCer(
        @Field("sessionId") sessionId: String,
        @Field("name") name: String,
        @Field("idNumber") idNumber: String,
        @Field("phonePath") phonePath: String
    ): Observable<BaseJson<Any>>
}