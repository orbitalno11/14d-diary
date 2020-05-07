package ac.th.kmutt.math.the14d_diary.ui.individualChat

import ac.th.kmutt.math.the14d_diary.helper.FirebaseHelper
import ac.th.kmutt.math.the14d_diary.model.ChatUserModel
import ac.th.kmutt.math.the14d_diary.model.MessageModel
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class ChatRoomViewModel : ViewModel() {

    private val firebaseHelper = FirebaseHelper.getInstance()
    private val messageList: MutableLiveData<List<MessageModel>> = MutableLiveData()

    // chat user data
    private lateinit var receiverID: String
    private lateinit var senderID: String
    private lateinit var senderName: String

    private lateinit var senderDatabase: DatabaseReference
    private lateinit var receiverDatabase: DatabaseReference

    fun setupChat(receiver: Bundle?){
        receiver?.let {
            this.receiverID = it.getString("userID")!!
            this.senderID = firebaseHelper.getAuth().currentUser!!.uid
            this.senderName = firebaseHelper.getAuth().currentUser!!.displayName!!

            this.senderDatabase = firebaseHelper.getMessageDatabaseRef().child("${this.senderID}_${this.receiverID}")
            this.receiverDatabase = firebaseHelper.getMessageDatabaseRef().child("${this.receiverID}_${this.senderID}")

            val participant = HashMap<String, Any>()
            val participant_1 = ChatUserModel()
            participant_1.displayName = firebaseHelper.getAuth().currentUser!!.displayName!!
            participant_1.userID = firebaseHelper.getAuth().currentUser!!.uid
            participant_1.picture = firebaseHelper.getAuth().currentUser!!.photoUrl.toString()

            val participant_2 = ChatUserModel()
            participant_2.displayName = it.getString("userName")!!
            participant_2.userID = it.getString("userID")!!
            participant_2.picture = it.getString("picture")!!

            participant["sender"] = participant_1
            participant["receiver"] = participant_2

            this.senderDatabase.updateChildren(participant)

            participant["sender"] = participant_2
            participant["receiver"] = participant_1

            this.receiverDatabase.updateChildren(participant)

        } ?: run {
            Log.e("CHAT ROOM", "NO USER BUNDLE")
        }
    }

    fun receivedMessage(): LiveData<List<MessageModel>> {
        val msg: MutableList<MessageModel> = mutableListOf()

        senderDatabase.child("message").addChildEventListener(object : ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {
//                TODO("Not yet implemented")
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
//                TODO("Not yet implemented")
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
//                TODO("Not yet implemented")
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val message = p0.getValue(MessageModel::class.java)
                message?.let {
                    msg.add(it)
                }
                messageList.value = msg
            }

            override fun onChildRemoved(p0: DataSnapshot) {
//                TODO("Not yet implemented")
            }

        })

        return messageList
    }

    fun sendMessage(message: String){
        val messageData = HashMap<String, String>()
        val date =
            SimpleDateFormat("dd-MM-yyyy HH:mm").format(Date().time)
        if (message != ""){
            messageData["message"] = message
            messageData["senderID"] = this.senderID
            messageData["sender"] = this.senderName
            messageData["sendTime"] = date
            this.senderDatabase.child("message").push().setValue(messageData)
            this.receiverDatabase.child("message").push().setValue(messageData)
        }
    }
}
