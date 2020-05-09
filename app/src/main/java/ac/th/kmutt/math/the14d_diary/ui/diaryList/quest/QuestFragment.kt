package ac.th.kmutt.math.the14d_diary.ui.diaryList.quest

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ac.th.kmutt.math.the14d_diary.R
import ac.th.kmutt.math.the14d_diary.adapter.DiaryItemAdapter
import ac.th.kmutt.math.the14d_diary.model.DiaryModel
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.diary_all_fragment.*

class QuestFragment : Fragment() {

    companion object {
        fun newInstance() =
            QuestFragment()
    }

    private val viewmodel by viewModels<QuestViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.diary_all_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewmodel.getQuestList().observe(viewLifecycleOwner, Observer {
            val rcv = diary_all_list
            val adapter = DiaryItemAdapter(context!!, it)
            rcv.adapter = adapter
            rcv.layoutManager = LinearLayoutManager(context)
        })

    }

}
