package ac.th.kmutt.math.the14d_diary.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class UserModel (
    var userID: String = "",
    var email: String = "",
    var displayName: String = ""
)