package ac.th.kmutt.math.the14d_diary.model

data class LineToken(
    var access_token: String = "",
    var toke_type: String = "",
    var refresh_token: String = "",
    var expires_in: Int = 0,
    var scope: String = "",
    var id_token: String = ""
)