package ac.th.kmutt.math.the14d_diary.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class DiaryModel(
    var diaryID: String = "",
    var diaryName: String = "",
    var diaryDetail: String = "",
    var imgUrl: String = "",
    var imgName: String = "",
    var share: Boolean = false,
    var diaryType: String = ""
) {
    @Exclude
    fun toMap(): Map<String, Any>{
        return mapOf(
            "diaryID" to diaryID,
            "diaryName" to diaryName,
            "diaryDetail" to diaryDetail,
            "imgUrl" to imgUrl,
            "imgName" to imgName,
            "share" to share
        )
    }
}