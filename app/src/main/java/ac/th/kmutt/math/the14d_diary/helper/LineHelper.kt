package ac.th.kmutt.math.the14d_diary.helper

import ac.th.kmutt.math.the14d_diary.Constants
import android.content.Context
import android.os.Bundle
import com.linecorp.linesdk.api.LineApiClient
import com.linecorp.linesdk.api.LineApiClientBuilder

class LineHelper(applicationContext: Context) {
    private var lineApiClient: LineApiClient = LineApiClientBuilder(applicationContext, Constants.LINE_CHANNEL_ID).build()
    private lateinit var lineProfile: Bundle

    fun getProfile(): Bundle {
        val response = lineApiClient.profile
        val displayName: String = response.responseData.displayName
        val picture: String = response.responseData.pictureUrl.toString()
        lineProfile.putString("displayName", displayName)
        lineProfile.putString("picture", picture)
        return lineProfile
    }

    fun logout(){
        lineApiClient.logout()
    }
}