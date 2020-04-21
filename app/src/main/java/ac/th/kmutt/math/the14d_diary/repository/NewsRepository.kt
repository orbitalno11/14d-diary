package ac.th.kmutt.math.the14d_diary.repository

import ac.th.kmutt.math.the14d_diary.Constants
import ac.th.kmutt.math.the14d_diary.model.NewsModel
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class NewsRepository {

    companion object {
        private var instance: NewsRepository? = null

        fun getInstance(): NewsRepository {
            if (instance == null) {
                instance = NewsRepository()
            }
            return instance as NewsRepository
        }
    }

    // create retrofit connection for thai api
    private fun createThaiAPI(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.TH_COVID_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // create retrofit connection for world api
    private fun createWorldAPI(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.GLOBAL_COVID_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

     suspend fun getThaiToday(): NewsModel{
        val retrofit = createThaiAPI()
        val service = retrofit.create(NewsService::class.java)
        return service.getThaiCovidToday()
    }

    suspend fun getWorldToday(): HashMap<String, Any>{
        val retrofit = createWorldAPI()
        val service = retrofit.create(NewsService::class.java)
        return service.getWorldCovidToday()

//        call.enqueue(object : Callback<HashMap<String, Any>>{
//            override fun onFailure(call: Call<HashMap<String, Any>>, t: Throwable) {
//                Log.d("NEWS", "Failed ${t.message}")
//            }
//
//            override fun onResponse(
//                call: Call<HashMap<String, Any>>,
//                response: Response<HashMap<String, Any>>
//            ) {
//                if (response.isSuccessful){
//                    val data = response.body()
//                    val global = data?.get("Global")
//                    Log.d("NEWS", "DATA $global")
//                }
//            }
//
//        })
    }
}