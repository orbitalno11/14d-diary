package ac.th.kmutt.math.the14d_diary.ui.chatHome.historyChat

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ac.th.kmutt.math.the14d_diary.R
import ac.th.kmutt.math.the14d_diary.adapter.ChatItemAdapter
import ac.th.kmutt.math.the14d_diary.model.ChatUserModel
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_chat_history_list.*

class ChatHistoryListFragment : Fragment() {

    companion object {
        fun newInstance() =
            ChatHistoryListFragment()
    }

    private val viewModel by viewModels<ChatHistoryListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat_history_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getHistoryList().observe(viewLifecycleOwner, Observer {

        })

        val chatList = ArrayList<ChatUserModel>()
        chatList.add(ChatUserModel("a", "aa"))
        chatList.add(ChatUserModel("b", "bb"))

        val rcv = chat_history_rcv
        val adapter = ChatItemAdapter(context!!, chatList)
        rcv.adapter = adapter
        rcv.layoutManager = LinearLayoutManager(context)
    }

}
