package ac.th.kmutt.math.the14d_diary.repository

import ac.th.kmutt.math.the14d_diary.model.NewsModel
import retrofit2.http.GET

interface NewsService {

    @GET("today")
    suspend fun getThaiCovidToday(): NewsModel

    @GET("summary")
    suspend fun getWorldCovidToday(): HashMap<String, Any>

}