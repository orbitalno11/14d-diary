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
            .child("chat/messages")
            .orderByKey()
            .startAt(userID)
            .addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.e(tag, "Can not listen to database.")
            }

            override fun onDataChange(p0: DataSnapshot) {
                Log.d(tag, "DATA ${p0.value}")
            }

        })

        return chatList
    }
}
