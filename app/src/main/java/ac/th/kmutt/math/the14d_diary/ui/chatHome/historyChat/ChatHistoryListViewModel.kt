package ac.th.kmutt.math.the14d_diary.ui.chatHome.historyChat

import ac.th.kmutt.math.the14d_diary.helper.FirebaseHelper
import ac.th.kmutt.math.the14d_diary.model.ChatUserModel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ChatHistoryListViewModel : ViewModel() {

    private val firebaseHelper = FirebaseHelper.getInstance()
    private val chatList: MutableLiveData<List<ChatUserModel>> = MutableLiveData()
    private val tag = ChatHistoryListViewModel::class.java.simpleName

    fun getHistoryList(): LiveData<List<ChatUserModel>> {
        val userID = firebaseHelper.getAuth().currentUser?.uid

        firebaseHelper.getDatabaseRef()
            .child("chat")
            .orderByChild("/sender/userID")
            .equalTo(userID)
            .addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.e(tag, "Can not listen to database.")
            }

            override fun onDataChange(p0: DataSnapshot) {
                val list: MutableList<ChatUserModel> = mutableListOf()
                for (data in p0.children){
                    val receiver = data.child("receiver")
                    val receiverData = receiver.getValue(ChatUserModel::class.java)
                    receiverData?.let {
                        list.add(it)
                    }
                }
                chatList.value = list
            }
        })
        return chatList
    }
}
