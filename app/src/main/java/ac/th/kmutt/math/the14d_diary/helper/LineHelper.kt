package ac.th.kmutt.math.the14d_diary.helper

import ac.th.kmutt.math.the14d_diary.Constants
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.linecorp.linesdk.api.LineApiClient
import com.linecorp.linesdk.api.LineApiClientBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class LineHelper(applicationContext: Context) : CoroutineScope {
    private var lineApiClient: LineApiClient =
        LineApiClientBuilder(applicationContext, Constants.LINE_CHANNEL_ID).build()
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    fun getProfile(): Bundle {
        val data = Bundle()
        val response = lineApiClient.profile
        val displayName: String = response.responseData.displayName
        val picture: String = response.responseData.pictureUrl.toString()
        data.putString("displayName", displayName)
        data.putString("picture", picture)
        return data
    }

    suspend fun logout() {
        withContext(Dispatchers.IO){
            lineApiClient.logout()
        }
    }

    fun verifyToken(): Boolean {
        val verifyResponse = lineApiClient.verifyToken()
        return if (verifyResponse.isSuccess){
            Log.i("LINE HELPER", "getResponseData: " + verifyResponse.responseData.toString())
            Log.i("LINE HELPER", "getResponseCode: " + verifyResponse.responseCode.toString())
            true
        }else {
            Log.i("LINE HELPER", "getResponseCode: " + verifyResponse.responseCode)
            Log.i("LINE HELPER", "getErrorData: " + verifyResponse.errorData)
            false
        }
    }

    suspend fun refreshToken() = withContext(Dispatchers.IO) {
        Log.d("LINE HELPER", "Refresh token")
        withContext(Dispatchers.Default) {
            lineApiClient.refreshAccessToken()
        }
        Log.d("LINE HELPER", "Refresh token finished")
    }
}