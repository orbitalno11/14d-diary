package ac.th.kmutt.math.the14d_diary.ui.chatHome.userList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ac.th.kmutt.math.the14d_diary.R
import ac.th.kmutt.math.the14d_diary.adapter.ChatItemAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_chat_list.*

class ChatListFragment : Fragment() {

    companion object {
        fun newInstance() =
            ChatListFragment()
    }

    private val viewModel: ChatListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getUserList().observe(viewLifecycleOwner, Observer {
            val rcv = chat_list_rcv
            val adapter = ChatItemAdapter(context!!, it)
            rcv.adapter = adapter
            rcv.layoutManager = LinearLayoutManager(context)
        })

    }

}
