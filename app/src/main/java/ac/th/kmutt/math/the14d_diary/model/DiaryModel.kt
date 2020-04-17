package ac.th.kmutt.math.the14d_diary.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class DiaryModel(
    var diaryID: String = "",
    var diaryName: String = "",
    var diaryDetail: String = "",
    var diaryImg: String = "",
    var diaryStatus: String = ""
) {
    @Exclude
    fun toMap(): Map<String, String>{
        return mapOf(
            "diaryID" to diaryID,
            "diaryName" to diaryName,
            "diaryDetail" to diaryDetail,
            "diaryImg" to diaryImg,
            "diaryStatus" to diaryStatus
        )
    }
}