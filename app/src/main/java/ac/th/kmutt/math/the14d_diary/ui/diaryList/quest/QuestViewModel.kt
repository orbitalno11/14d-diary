package ac.th.kmutt.math.the14d_diary.ui.diaryList.quest

import ac.th.kmutt.math.the14d_diary.helper.FirebaseHelper
import ac.th.kmutt.math.the14d_diary.model.DiaryModel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class QuestViewModel : ViewModel() {

    private val firebaseHelper = FirebaseHelper.getInstance()
    private val questList: MutableLiveData<List<DiaryModel>> = MutableLiveData()
    private val mAuth = firebaseHelper.getAuth()
    private val userID = mAuth.currentUser?.uid
    private val tag = QuestViewModel::class.java.simpleName

    fun getQuestList(): LiveData<List<DiaryModel>>{
        firebaseHelper.getQuestRef()
            .addValueEventListener(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    Log.e(tag, "Can not listen to database", p0.toException())
                }

                override fun onDataChange(p0: DataSnapshot) {
                    val list: MutableList<DiaryModel> = mutableListOf()

                    for (data in p0.children){
                        var imgUrl: String = ""
                        var imgName: String = ""
                        if (data.hasChild("user")) {
                            val user = data.child("user/$userID").value as HashMap<*, *>
                            imgUrl = user["imgUrl"].toString()
                            imgName = user["imgName"].toString()
                        }
                        val quest = data.getValue(DiaryModel::class.java)
                        quest?.apply {
                            this.diaryID = data.key!!
                            this.imgName = imgName
                            this.imgUrl = imgUrl
                        }?.also {
                            list.add(it)
                        }
                    }

                    questList.value = list
                }

            })
        return questList
    }

}
