package ac.th.kmutt.math.the14d_diary.ui.diary

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ac.th.kmutt.math.the14d_diary.R
import ac.th.kmutt.math.the14d_diary.helper.AppbarHelper

class DiaryFragment : Fragment() {

    companion object {
        fun newInstance() = DiaryFragment()
    }

    private lateinit var viewModel: DiaryViewModel
    private lateinit var appbarHelper: AppbarHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_diary, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DiaryViewModel::class.java)
        setupAppbar()
    }

    private fun setupAppbar(){
        appbarHelper = AppbarHelper(view!!)
        appbarHelper.setMunuIcon(R.drawable.ic_chevron_left_white_24dp)
        appbarHelper.setTitle("Diary 1")
        appbarHelper.setMenuClickListener(View.OnClickListener {
            activity?.onBackPressed()
        })
    }

}
