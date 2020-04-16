package ac.th.kmutt.math.the14d_diary.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class ChatUserModel(
    var userID: String = "",
    var userName: String = "",
    var userImg: String = ""
) {

    @Exclude
    fun toMap(): Map<String, String>{
        return mapOf(
            "userID" to userID,
            "userName" to userName,
            "userImg" to userImg
        )
    }
}