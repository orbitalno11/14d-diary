package ac.th.kmutt.math.the14d_diary.ui.news.thaiNews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ac.th.kmutt.math.the14d_diary.R
import ac.th.kmutt.math.the14d_diary.epoxy.Controller
import ac.th.kmutt.math.the14d_diary.model.InflectNewsModel
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_news_thai.*

class ThaiNewsFragment : Fragment() {

    companion object {
        fun newInstance() =
            ThaiNewsFragment()
    }

    private val viewModel: ThaiNewsViewModel by viewModels()
    private var newsToday: InflectNewsModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_thai, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getThaiToday().observe(viewLifecycleOwner, Observer {
            newsToday = it

            newsToday?.let {
                setupData()
            }
        })

        val controller = Controller()

        news_rcv.layoutManager = LinearLayoutManager(context)
        news_rcv.setHasFixedSize(false)
        news_rcv.setController(controller)

        viewModel.getTodayNews().observe(viewLifecycleOwner, Observer {
            controller.apply {
                newsItems = it
            }
        })

    }

    private fun setupData(){
        news_update_time.text = newsToday?.UpdateDate
        news_inflect_number.text = newsToday?.Confirmed.toString()
        news_recover_number.text = newsToday?.Recovered.toString()
        news_treat_number.text  = newsToday?.Hospitalized.toString()
        news_death_number.text = newsToday?.Deaths.toString()
        news_new_inflect_number.text = newsToday?.NewConfirmed.toString()
        news_new_recover_number.text = newsToday?.NewRecovered.toString()
        news_new_death_number.text = newsToday?.NewDeaths.toString()
    }

}
