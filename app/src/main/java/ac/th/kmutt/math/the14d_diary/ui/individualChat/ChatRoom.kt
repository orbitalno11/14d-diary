package ac.th.kmutt.math.the14d_diary.ui.individualChat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ac.th.kmutt.math.the14d_diary.R
import ac.th.kmutt.math.the14d_diary.adapter.MessageAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_chat_room.*

class ChatRoom : Fragment() {

    companion object {
        fun newInstance() = ChatRoom()
    }

    private val viewModel: ChatRoomViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat_room, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(ChatRoomViewModel::class.java)
        viewModel.recievedMessage().observe(viewLifecycleOwner, Observer {
            val adapter = MessageAdapter(context!!, it)
            val manager = LinearLayoutManager(context)
            val rcv = message_view
            rcv.adapter = adapter
            manager.stackFromEnd = true
            rcv.layoutManager = manager
        })
    }

}
