package ac.th.kmutt.math.the14d_diary.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ac.th.kmutt.math.the14d_diary.R
import ac.th.kmutt.math.the14d_diary.adapter.ChatListAdapter
import ac.th.kmutt.math.the14d_diary.model.ChatUserModel
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_chat_history_list.*
import kotlinx.android.synthetic.main.fragment_chat_list.*

class ChatHistoryListFragment : Fragment() {

    companion object {
        fun newInstance() = ChatHistoryListFragment()
    }

    private lateinit var viewModel: ChatHistoryListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat_history_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ChatHistoryListViewModel::class.java)
        // TODO: Use the ViewModel

        val chatList = ArrayList<ChatUserModel>()
        chatList.add(ChatUserModel("a", "aa"))
        chatList.add(ChatUserModel("b", "bb"))

        val rcv = chat_history_rcv
        val adapter = ChatListAdapter(context!!, chatList)
        rcv.adapter = adapter
        rcv.layoutManager = LinearLayoutManager(context)
    }

}
