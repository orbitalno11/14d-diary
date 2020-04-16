package ac.th.kmutt.math.the14d_diary.ui.chatHome

import ac.th.kmutt.math.the14d_diary.MainActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ac.th.kmutt.math.the14d_diary.R
import ac.th.kmutt.math.the14d_diary.helper.AppbarHelper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import kotlinx.android.synthetic.main.fragment_chat_home.*

class ChatHomeFragment : Fragment() {

    companion object {
        fun newInstance() = ChatHomeFragment()
    }

    private val viewModel: ChatHomeViewModel by viewModels()
    private lateinit var appbarHelper: AppbarHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupAppbar()
        // TODO: Use the ViewModel

        chat_tab.addTab(chat_tab.newTab().setText("CHAT"))
        chat_tab.addTab(chat_tab.newTab().setText("CHAT2"))
    }

    private fun setupAppbar(){
        appbarHelper = AppbarHelper(view!!)
        appbarHelper.setTitle(resources.getString(R.string.nav_chat))
        appbarHelper.setMenuNav(activity as MainActivity)
    }

}
