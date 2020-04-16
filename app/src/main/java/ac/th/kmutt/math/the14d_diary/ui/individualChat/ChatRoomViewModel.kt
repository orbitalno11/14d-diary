package ac.th.kmutt.math.the14d_diary.ui.individualChat

import ac.th.kmutt.math.the14d_diary.helper.FirebaseHelper
import ac.th.kmutt.math.the14d_diary.model.MessageModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChatRoomViewModel : ViewModel() {

    private val firebaseHelper = FirebaseHelper.getInstance()
    private val messageList: MutableLiveData<List<MessageModel>> = MutableLiveData()

    fun receivedMessage(): LiveData<List<MessageModel>> {
        val msg: MutableList<MessageModel> = mutableListOf()
        msg.add(MessageModel("14D-Diary", "14d-diary", "Hello", "19.00"))
        msg.add(MessageModel("aob", "aob_id", "Hi", "19.00"))

        messageList.value = msg
        return messageList
    }

    fun sendMessage(){

    }
}
