package ac.th.kmutt.math.the14d_diary.fragment

import ac.th.kmutt.math.the14d_diary.model.InflectNewsModel
import ac.th.kmutt.math.the14d_diary.model.NewsModel
import ac.th.kmutt.math.the14d_diary.model.ReceivedNewsModel
import ac.th.kmutt.math.the14d_diary.repository.NewsRepository
import ac.th.kmutt.math.the14d_diary.repository.NewsService
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ThaiNewsViewModel : ViewModel() {
    private val newsRepository = NewsRepository.getInstance()
    private val newThaiToday: MutableLiveData<InflectNewsModel> = MutableLiveData()
    private val todayNews: MutableLiveData<List<NewsModel>> = MutableLiveData()

    fun getThaiToday(): LiveData<InflectNewsModel> {
        CoroutineScope(IO).launch {
            val news = async {
                newsRepository.getThaiToday()
            }.await()

            newThaiToday.postValue(news)
        }
        return newThaiToday
    }

    fun getTodayNews(): LiveData<List<NewsModel>> {
        val retrofit = newsRepository.createNewsAPI()
        val service = retrofit.create(NewsService::class.java)
        val call = service.getThaiNews()

        call.enqueue(object : Callback<ReceivedNewsModel>{
            override fun onFailure(call: Call<ReceivedNewsModel>, t: Throwable) {
                Log.d("NEWS", "FAILED ${t.message}")
            }

            override fun onResponse(call: Call<ReceivedNewsModel>, response: Response<ReceivedNewsModel>) {
                if (response.isSuccessful){
                    val data = response.body()

                    data?.let {
                        todayNews.value = it.articles
                    }
                }else{
                    Log.d("NEWS", "DATA ${response.body()}")
                }
            }

        })
        return todayNews
    }
}
