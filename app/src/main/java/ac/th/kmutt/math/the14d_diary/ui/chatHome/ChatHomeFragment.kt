package ac.th.kmutt.math.the14d_diary.ui.chatHome

import ac.th.kmutt.math.the14d_diary.MainActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ac.th.kmutt.math.the14d_diary.R
import ac.th.kmutt.math.the14d_diary.SignIn
import ac.th.kmutt.math.the14d_diary.adapter.ChatTabAdapter
import ac.th.kmutt.math.the14d_diary.helper.AppbarHelper
import android.content.Intent
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_chat_home.*

class ChatHomeFragment : Fragment() {

    companion object {
        fun newInstance() = ChatHomeFragment()
    }

    private lateinit var appbarHelper: AppbarHelper
    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (mAuth.currentUser == null) {
            startActivity(Intent(context, SignIn::class.java))
            activity?.finish()
            null
        }else{
            inflater.inflate(R.layout.fragment_chat_home, container, false)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupAppbar()

        // setup tab layout and viewpager
        view?.let {
            val tab_layout = chat_tab
            chat_viewpager.adapter = ChatTabAdapter(this)
            val tabName: Array<String> = resources.getStringArray(R.array.tab_chat)
            TabLayoutMediator(tab_layout, chat_viewpager){ tab, position ->
                tab.text = tabName[position]
            }.attach()
        }
    }

    private fun setupAppbar(){
        view?.let {
            appbarHelper = AppbarHelper(it)
            appbarHelper.setTitle(resources.getString(R.string.nav_chat))
            appbarHelper.setMenuNav(activity as MainActivity)
        }
    }

}
