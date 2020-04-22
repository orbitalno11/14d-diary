package ac.th.kmutt.math.the14d_diary.fragment

import ac.th.kmutt.math.the14d_diary.model.InflectNewsModel
import ac.th.kmutt.math.the14d_diary.repository.NewsRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONObject
import kotlin.math.roundToInt

class GlobalNewsViewModel : ViewModel() {
    private val newsRepository = NewsRepository.getInstance()
    private val newsToday: MutableLiveData<InflectNewsModel> = MutableLiveData()

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
