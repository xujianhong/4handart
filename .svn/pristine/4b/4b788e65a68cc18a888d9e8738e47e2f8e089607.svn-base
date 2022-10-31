package com.daomingedu.art.bean

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class StudentBean(
    val CosPrefix: String,
    val remaTime: Int,
    val remark: String,
    val student: StudentBeanStudent,
    val testSignMajorList: List<StudentBeanTestSignMajor>
){
    companion object {
        fun getData(result: String): StudentBean {
            return Gson().fromJson(result, object : TypeToken<StudentBean>() {}.type)
        }
    }
}

data class StudentBeanStudent(
    val birthday: String,
    val name: String,
    val photo: String,
    val sex: String
)

data class StudentBeanTestSignMajor(
    val endUploadDate: String,
    val getCert: Boolean,
    val grading: Boolean,
    val id: String,
    val majorId: String,
    val majorLevelId: String,
    val majorLevelName: String,
    val majorName: String,
    val musicVideoList: List<StudentBeanMusicVideo>,
    val score: Any,
    val startUploadDate: String,
    val testId: String,
    val testMajorResultId: String,
    val testName: String,
    val testNumber: String,
    val testSignId: String,
    val remaTime: Long,
    val isLimitTime: Int,
    val uploadLimitTime: Int
)

data class StudentBeanMusicVideo(
    val statusText: String,
    val auditDate: Any,
    val auditUser: String,
    val createTime: String,
    val id: String,
    val imgA: String,
    val imgAScore: Int,
    val imgB: String,
    val imgBScore: Int,
    val isAudit: Any,
    val majorId: String,
    val majorLevelId: String,
    val majorLevelSongId: String,
    val majorLevelSongImg: String,
    val musicSongId: String,
    val studentId: String,
    val testId: String,
    val testSignId: String,
    val testSignMajorId: String,
    val typeId: String,
    val uploadDate: Any,
    val uploadNum: Any,
    val videoCreateData: String,
    val videoPath: String,
    val videoSize: String,
    val videoTransPath: String
){
    companion object {
        fun getListData(result: String): ArrayList<StudentBeanMusicVideo> {
            return Gson().fromJson(result, object : TypeToken<ArrayList<StudentBeanMusicVideo>>() {}.type)
        }
    }
}