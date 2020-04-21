package ac.th.kmutt.math.the14d_diary.fragment

import ac.th.kmutt.math.the14d_diary.model.NewsModel
import ac.th.kmutt.math.the14d_diary.repository.NewsRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class ThaiNewsViewModel : ViewModel() {
    private val newsRepository = NewsRepository.getInstance()
    private val newThaiToday: MutableLiveData<NewsModel> = MutableLiveData()

    fun getThaiToday(): LiveData<NewsModel>{
        CoroutineScope(IO).launch {
            val news = async {
                newsRepository.getThaiToday()
            }.await()

            newThaiToday.postValue(news)
        }
        return newThaiToday
    }
}
