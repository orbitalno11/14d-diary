package ac.th.kmutt.math.the14d_diary.ui.news

import ac.th.kmutt.math.the14d_diary.MainActivity
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ac.th.kmutt.math.the14d_diary.R
import ac.th.kmutt.math.the14d_diary.adapter.ChatTabAdapter
import ac.th.kmutt.math.the14d_diary.adapter.NewsTabAdapter
import ac.th.kmutt.math.the14d_diary.helper.AppbarHelper
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_chat_home.*
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : Fragment() {

    companion object {
        fun newInstance() = NewsFragment()
    }

    private lateinit var viewModel: NewsViewModel
    private lateinit var appbarHelper: AppbarHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)
        setupAppbar()
        // TODO: Use the ViewModel

        // setup tab layout and viewpager
        val tab_layout = news_tab
        news_viewpager.adapter = NewsTabAdapter(this)
        val tabName: Array<String> = resources.getStringArray(R.array.tab_news)
        TabLayoutMediator(tab_layout, news_viewpager){ tab, position ->
            tab.text = tabName[position]
        }.attach()
    }

    private fun setupAppbar(){
        appbarHelper = AppbarHelper(view!!)
        appbarHelper.setTitleVisible(View.INVISIBLE)
        appbarHelper.setMenuNav((activity as MainActivity))
    }

}
