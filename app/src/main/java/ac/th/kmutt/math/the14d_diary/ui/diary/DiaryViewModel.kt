package ac.th.kmutt.math.the14d_diary.ui.diary

import ac.th.kmutt.math.the14d_diary.helper.FirebaseHelper
import ac.th.kmutt.math.the14d_diary.model.DiaryModel
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import kotlin.coroutines.CoroutineContext

class DiaryViewModel : ViewModel(), CoroutineScope {

    private val firebaseHelper = FirebaseHelper.getInstance()
    private val diaryDetail: MutableLiveData<DiaryModel> = MutableLiveData()
    private val submitStatus: MutableLiveData<Boolean> = MutableLiveData()
    private val mAuth = firebaseHelper.getAuth()
    private val userID = mAuth.currentUser?.uid
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference
    private val tag = DiaryViewModel::class.java.simpleName
    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    fun getDiary(diaryID: String, diaryType: String): LiveData<DiaryModel> {
        val db = if (diaryType == "quest") {
            firebaseHelper.getQuestRef()
        } else {
            firebaseHelper.getDiaryRef()
        }

        db.child(diaryID).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.e(tag, "Can not listen to database.", p0.toException())
            }

            override fun onDataChange(p0: DataSnapshot) {
                var imgUrl: String = ""
                var imgName: String = ""
                if (p0.hasChild("user")) {
                    val user = p0.child("user/$userID").value as HashMap<*, *>
                    imgUrl = user["imgUrl"].toString()
                    imgName = user["imgName"].toString()
                }
                val diary = p0.getValue(DiaryModel::class.java)
                diary?.apply {
                    this.diaryID = p0.key!!
                    this.imgName = imgName
                    this.imgUrl = imgUrl
                }?.also {
                    diaryDetail.value = it
                }
            }
        })

        return diaryDetail
    }

    fun submitQuest(diary: DiaryModel, picture: Bitmap): LiveData<Boolean> {
        val db = if (diary.diaryType == "quest") {
            firebaseHelper.getQuestRef()
        } else {
            firebaseHelper.getDiaryRef()
        }

        val userData = HashMap<String, String>()
        userData["userID"] = userID!!

        launch {
            val data = uploadImage(diary.diaryType, diary.diaryName, picture)
            Log.d(tag, "IMG DATA: $data")
            userData["imgName"] = data["imgName"].toString()
            userData["imgUrl"] = data["imgUrl"].toString()
            val update = HashMap<String, Any>()
            update["/${diary.diaryID}/user/$userID"] = userData

            db.updateChildren(update)
        }

        return submitStatus
    }

    private suspend fun uploadImage(
        type: String,
        name: String,
        picture: Bitmap
    ): HashMap<String, String> {
        val fileName = "${type}-${name}-$userID.jpg"
        val imgRef = storageRef.child(fileName)
        val baos = ByteArrayOutputStream()
        picture.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val imageData = baos.toByteArray()

        val uploadTask = imgRef.putBytes(imageData)

        val value = uploadTask
            .continueWithTask { task ->
                imgRef.downloadUrl
            }
            .addOnFailureListener {
                Log.e(tag, "Can not upload image.", it.cause)
            }
            .addOnCompleteListener { task ->
                Log.d(tag, "Upload image finished.")
            }.await()

        val url = value.toString()
        val delimiter = "&token="
        val splitString = url.split(delimiter)
        val imgUrl = splitString[0]

        val out = HashMap<String, String>()
        out["imgName"] = fileName
        out["imgUrl"] = imgUrl
        return out
    }
}
