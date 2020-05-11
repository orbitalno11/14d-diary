package ac.th.kmutt.math.the14d_diary.ui.home

import ac.th.kmutt.math.the14d_diary.MainActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ac.th.kmutt.math.the14d_diary.R
import ac.th.kmutt.math.the14d_diary.epoxy.HomeController
import ac.th.kmutt.math.the14d_diary.helper.AppbarHelper
import ac.th.kmutt.math.the14d_diary.model.NewsModel
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var appbarHelper: AppbarHelper
    private val viewmodel by viewModels<HomeViewModel>()
    private var newsList: List<NewsModel>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupAppbar()

        val navController =
            Navigation.findNavController(context as AppCompatActivity, R.id.fragment_host)

        val emergencyClick = View.OnClickListener{
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:*1422")
            startActivity(intent)
        }

        val chatClick = View.OnClickListener{
            navController.navigate(R.id.action_nav_home_to_nav_chat)
        }

        val diaryClick = View.OnClickListener{
            navController.navigate(R.id.action_nav_home_to_nav_diary)
        }

        val newsClick = View.OnClickListener{
            navController.navigate(R.id.action_nav_home_to_nav_news)
        }

        val controller = HomeController().apply {
            setNews(null)
            setEmergencyClick(emergencyClick)
            setChatClick(chatClick)
            setNewsClick(newsClick)
            setDiaryClick(diaryClick)
        }

        home_news_rcv.layoutManager = LinearLayoutManager(context)
        home_news_rcv.setHasFixedSize(false)
        home_news_rcv.setController(controller)

        viewmodel.getTodayNews().observe(viewLifecycleOwner, Observer {
            controller.apply {
                this.setNews(it)
            }
        })
    }

    private fun setupAppbar() {
        appbarHelper = AppbarHelper(view!!)
        appbarHelper.setTitleVisible(View.INVISIBLE)
        appbarHelper.setMenuClickListener(View.OnClickListener {
            (activity as MainActivity).openDrawer()
        })
    }

}
