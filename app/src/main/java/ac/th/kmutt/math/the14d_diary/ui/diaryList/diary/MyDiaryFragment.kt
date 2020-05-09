package ac.th.kmutt.math.the14d_diary.ui.diaryList.diary

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ac.th.kmutt.math.the14d_diary.R
import ac.th.kmutt.math.the14d_diary.adapter.DiaryItemAdapter
import ac.th.kmutt.math.the14d_diary.model.DiaryModel
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.diary_all_fragment.*
import kotlinx.android.synthetic.main.my_diary_fragment.*

class MyDiaryFragment : Fragment() {

    companion object {
        fun newInstance() =
            MyDiaryFragment()
    }

    private val viewmodel by viewModels<MyDiaryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.my_diary_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupAddDiaryButton()

        viewmodel.getMyDiary().observe(viewLifecycleOwner, Observer {
            val rcv = my_diary_rcv
            val adapter = DiaryItemAdapter(context!!, it)
            rcv.adapter = adapter
            rcv.layoutManager = LinearLayoutManager(context)
        })

    }

    private fun setupAddDiaryButton(){
        val navController = Navigation.findNavController(context as AppCompatActivity, R.id.fragment_host)

        add_diary.setOnClickListener {
            navController.navigate(R.id.action_nav_diary_to_diaryFragment)
        }

    }

}
