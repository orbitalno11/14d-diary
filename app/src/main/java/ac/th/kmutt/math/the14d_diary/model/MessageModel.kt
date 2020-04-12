package ac.th.kmutt.math.the14d_diary.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class MessageModel(
    var sender: String = "",
    var senderID: String = "",
    var message: String = "",
    var sendTime: String = ""
) {

    @Exclude
    fun toMap(): Map<String, String>{
        return mapOf(
            "sender" to sender,
            "senderID" to senderID,
            "message" to message,
            "sendTime" to sendTime
        )
    }
}