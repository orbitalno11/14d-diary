package ac.th.kmutt.math.the14d_diary.ui.diaryList

import ac.th.kmutt.math.the14d_diary.MainActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ac.th.kmutt.math.the14d_diary.R
import ac.th.kmutt.math.the14d_diary.SignIn
import ac.th.kmutt.math.the14d_diary.adapter.DiaryTabAdapter
import ac.th.kmutt.math.the14d_diary.helper.AppbarHelper
import android.content.Intent
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_diary_list.*

class DiaryListFragment : Fragment() {

    companion object {
        fun newInstance() = DiaryListFragment()
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
        } else {
            return inflater.inflate(R.layout.fragment_diary_list, container, false)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        view?.let {
            setupAppbar()

            // setup tab layout and view pager
            val tab_layout = diary_tab
            diary_viewpager.adapter = DiaryTabAdapter(this)
            val tabName: Array<String> = resources.getStringArray(R.array.tab_diary)
            TabLayoutMediator(tab_layout, diary_viewpager) { tab, position ->
                tab.text = tabName[position]
            }.attach()
        }

    }

    private fun setupAppbar() {
        appbarHelper = AppbarHelper(view!!)
        appbarHelper.setTitle(resources.getString(R.string.nav_diary))
        appbarHelper.setMenuNav(activity as MainActivity)
    }

}
