package ac.th.kmutt.math.the14d_diary.ui.individualChat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ac.th.kmutt.math.the14d_diary.R
import ac.th.kmutt.math.the14d_diary.adapter.MessageAdapter
import ac.th.kmutt.math.the14d_diary.helper.AppbarHelper
import ac.th.kmutt.math.the14d_diary.model.MessageModel
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_chat_room.*

class ChatRoomFragment : Fragment() {

    companion object {
        fun newInstance() = ChatRoomFragment()
    }

    private val viewModel: ChatRoomViewModel by viewModels()
    private lateinit var appbarHelper: AppbarHelper
    private lateinit var userName: String
    private lateinit var receiverID: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat_room, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(ChatRoomViewModel::class.java)
        this.userName = arguments?.getString("userName")!!
        this.receiverID = arguments?.getString("userID")!!
        setupAppbar()
        viewModel.setupChat(arguments)
        viewModel.receivedMessage().observe(viewLifecycleOwner, Observer {
            addMessage(it)
            message_text.text.clear()
        })

        message_send_button.setOnClickListener{
            val message = message_text.text.toString()
            viewModel.sendMessage(message)
        }
    }

    private fun setupAppbar(){
        appbarHelper = AppbarHelper(view!!)
        appbarHelper.setMunuIcon(R.drawable.ic_chevron_left_white_24dp)
        appbarHelper.setTitle(this.userName)
        appbarHelper.setMenuClickListener(View.OnClickListener {
            activity?.onBackPressed()
        })
    }

    private fun addMessage(message: List<MessageModel>){
        val rcv = message_view
        val adapter = MessageAdapter(context!!, message)
        rcv.adapter = adapter
        val manager = LinearLayoutManager(context)
        manager.stackFromEnd = true
        rcv.layoutManager = manager
    }

}
