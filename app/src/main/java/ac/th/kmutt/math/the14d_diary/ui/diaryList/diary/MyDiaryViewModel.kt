package ac.th.kmutt.math.the14d_diary.ui.diaryList.diary

import ac.th.kmutt.math.the14d_diary.helper.FirebaseHelper
import ac.th.kmutt.math.the14d_diary.model.DiaryModel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MyDiaryViewModel : ViewModel() {

    private val firebaseHelper = FirebaseHelper.getInstance()
    private val diaryList: MutableLiveData<List<DiaryModel>> = MutableLiveData()
    private val mAuth = firebaseHelper.getAuth()
    private val userID = mAuth.currentUser?.uid
    private val tag = MyDiaryViewModel::class.java.simpleName

    fun getMyDiary(): LiveData<List<DiaryModel>> {
        firebaseHelper.getDiaryRef()
            .orderByKey()
            .equalTo("diary-$userID")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Log.e(tag, "Can not listen to database", p0.toException())
                }

                override fun onDataChange(p0: DataSnapshot) {
                    val list: MutableList<DiaryModel> = mutableListOf()
                    if (p0.hasChild("diary-$userID")) {
                        for (data in p0.child("diary-$userID").children) {
                            val diary = data.getValue(DiaryModel::class.java)
                            diary?.apply {
                                this.diaryID = data.key!!
                            }?.also {
                                list.add(it)
                            }
                        }
                    }
                    diaryList.value = list
                }
            })
        return diaryList
    }

}
