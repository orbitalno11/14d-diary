package ac.th.kmutt.math.the14d_diary.helper

import ac.th.kmutt.math.the14d_diary.Constants
import android.content.Context
import android.os.Bundle
import com.linecorp.linesdk.api.LineApiClient
import com.linecorp.linesdk.api.LineApiClientBuilder
import kotlinx.coroutines.*
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
}