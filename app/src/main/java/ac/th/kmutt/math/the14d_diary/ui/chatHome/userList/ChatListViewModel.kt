package ac.th.kmutt.math.the14d_diary.ui.chatHome.userList

import ac.th.kmutt.math.the14d_diary.helper.FirebaseHelper
import ac.th.kmutt.math.the14d_diary.model.ChatUserModel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class ChatListViewModel : ViewModel(), CoroutineScope {

    private val firebaseHelper = FirebaseHelper.getInstance()
    private val chatList: MutableLiveData<List<ChatUserModel>> = MutableLiveData()
    private val job = Job()
    private val tag = ChatListViewModel::class.java.simpleName

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    fun getUserList(): LiveData<List<ChatUserModel>> {

        firebaseHelper.getUserRef().addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Log.e(tag, "Can not listen to database.")
            }

            override fun onDataChange(p0: DataSnapshot) {
                val userID = firebaseHelper.getAuth().currentUser?.uid
                val userList: MutableList<ChatUserModel> = mutableListOf()
                for (data in p0.children){
                    if (userID != data.key){
                        val value = data.getValue(ChatUserModel::class.java)
                        value?.apply {
                            this.userID = data.key!!
                        }?.also {
                            userList.add(it)
                        }
                    }
                }
                chatList.value = userList
            }

        })

        return chatList
    }
}
