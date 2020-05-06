package ac.th.kmutt.math.the14d_diary.ui.news.globalNews

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
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.roundToInt

class GlobalNewsViewModel : ViewModel() {
    private val newsRepository = NewsRepository.getInstance()
    private val newsToday: MutableLiveData<InflectNewsModel> = MutableLiveData()
    private val todayNews: MutableLiveData<List<NewsModel>> = MutableLiveData()

    fun getToday(): LiveData<InflectNewsModel>{
        CoroutineScope(IO).launch {
            val data = async {
                newsRepository.getWorldToday()
            }.await()

            val news = InflectNewsModel()
            val global = data["Global"].toString()
            val map = jsonToMap(global)

            news.Confirmed = map["TotalConfirmed"]!!.toDouble().roundToInt()
            news.Deaths = map["TotalDeaths"]!!.toDouble().roundToInt()
            news.Recovered = map["TotalRecovered"]!!.toDouble().roundToInt()
            news.NewConfirmed = map["NewConfirmed"]!!.toDouble().roundToInt()
            news.NewDeaths = map["NewDeaths"]!!.toDouble().roundToInt()
            news.NewRecovered = map["NewRecovered"]!!.toDouble().roundToInt()

            newsToday.postValue(news)
        }
        return newsToday
    }

    fun getTodayNews(): LiveData<List<NewsModel>> {
        val retrofit = newsRepository.createNewsAPI()
        val service = retrofit.create(NewsService::class.java)
        val call = service.getUsNews()

        call.enqueue(object : Callback<ReceivedNewsModel> {
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

    private fun jsonToMap(string: String) : HashMap<String, String>{
        val map: HashMap<String, String> = HashMap()
        val jsonObject = JSONObject(string)
        val keys = jsonObject.keys()

        while (keys.hasNext()){
            val key = keys.next()
            val value = jsonObject.getString(key)
            map[key] = value
        }

        return map
    }
}
