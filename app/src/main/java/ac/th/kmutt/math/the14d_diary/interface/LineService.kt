package ac.th.kmutt.math.the14d_diary.`interface`

import ac.th.kmutt.math.the14d_diary.model.FirebaseCustomToken
import ac.th.kmutt.math.the14d_diary.model.LineToken
import retrofit2.Call
import retrofit2.http.*

interface LineService {
    @FormUrlEncoded
    @POST("token/")
    fun getToken(
        @Field("grant_type") grant_type: String,
        @Field("code") code: String,
        @Field("redirect_uri") uri: String,
        @Field("client_id") client_id: String,
        @Field("client_secret") client_secret: String
    ): Call<LineToken>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("createToken/")
    fun createCustomToken(
        @Body body: HashMap<String, String>
    ): Call<FirebaseCustomToken>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("getToken/")
    suspend fun getMemberToken(
        @Body body: HashMap<String, String>
    ):FirebaseCustomToken

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("checkUser/")
    fun checkUser(
        @Body body: HashMap<String, String>
    ): Call<String>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("setNotifyToken/")
    fun setNotifyToken(
        @Body body: HashMap<String, String?>
    ): Call<HashMap<String, Any>>
}