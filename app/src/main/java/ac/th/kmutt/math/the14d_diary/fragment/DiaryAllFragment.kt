package ac.th.kmutt.math.the14d_diary.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ac.th.kmutt.math.the14d_diary.R
import ac.th.kmutt.math.the14d_diary.adapter.DiaryItemAdapter
import ac.th.kmutt.math.the14d_diary.model.DiaryModel
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.diary_all_fragment.*

class DiaryAllFragment : Fragment() {

    companion object {
        fun newInstance() = DiaryAllFragment()
    }

    private lateinit var viewModel: DiaryAllViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.diary_all_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DiaryAllViewModel::class.java)
        // TODO: Use the ViewModel

        val diaryList = ArrayList<DiaryModel>()
        diaryList.add(DiaryModel("d1", "Day 1", "DDDDDD"))
        diaryList.add(DiaryModel("d2", "Day 2", "DDDDDDDDDDDDDD"))

        val rcv = diary_all_list
        val adapter = DiaryItemAdapter(context!!, diaryList)
        rcv.adapter = adapter
        rcv.layoutManager = LinearLayoutManager(context)
    }

}
