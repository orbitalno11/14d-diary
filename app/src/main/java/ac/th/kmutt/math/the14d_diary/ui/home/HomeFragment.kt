package ac.th.kmutt.math.the14d_diary.ui.home

import ac.th.kmutt.math.the14d_diary.MainActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ac.th.kmutt.math.the14d_diary.R
import ac.th.kmutt.math.the14d_diary.helper.AppbarHelper
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_appbar.*

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var appbarHelper: AppbarHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupAppbar()

        val navController = Navigation.findNavController(context as AppCompatActivity, R.id.fragment_host)

        chat_button.setOnClickListener {
            val mAuth = FirebaseAuth.getInstance()
            mAuth.signOut()
//            navController.navigate(R.id.action_nav_home_to_nav_chat)
        }

        diary_button.setOnClickListener {
            navController.navigate(R.id.action_nav_home_to_nav_diary)
        }

        news_button.setOnClickListener {
            navController.navigate(R.id.action_nav_home_to_nav_news)
        }
    }

    private fun setupAppbar(){
        appbarHelper = AppbarHelper(view!!)
        appbarHelper.setTitleVisible(View.INVISIBLE)
        appbarHelper.setMenuClickListener(View.OnClickListener {
            (activity as MainActivity).openDrawer()
        })
    }

}
