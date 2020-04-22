package ac.th.kmutt.math.the14d_diary.repository

import ac.th.kmutt.math.the14d_diary.Constants
import ac.th.kmutt.math.the14d_diary.model.InflectNewsModel
import ac.th.kmutt.math.the14d_diary.model.ReceivedNewsModel
import retrofit2.Call
import retrofit2.http.GET

interface NewsService {

    @GET("today")
    suspend fun getThaiCovidToday(): InflectNewsModel

    @GET("summary")
    suspend fun getWorldCovidToday(): HashMap<String, Any>

    @GET("top-headlines?country=th&category=health&apiKey=${Constants.NEWS_API_KEY}")
    fun getThaiNews(): Call<ReceivedNewsModel>

    @GET("top-headlines?country=us&category=health&apiKey=${Constants.NEWS_API_KEY}")
    fun getUsNews(): Call<ReceivedNewsModel>

}