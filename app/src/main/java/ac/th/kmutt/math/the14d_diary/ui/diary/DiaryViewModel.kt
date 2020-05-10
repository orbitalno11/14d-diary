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
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.HashMap
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
        if (diaryType == "quest") {
            firebaseHelper.getQuestRef()
                .child(diaryID).addValueEventListener(object : ValueEventListener {
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
        } else {
            firebaseHelper.getDiaryRef()
                .child("diary-$userID/$diaryID")
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        Log.e(tag, "Can not listen to database.", p0.toException())
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val diary = p0.getValue(DiaryModel::class.java)
                        diary?.apply {
                            this.diaryID = p0.key!!
                        }?.also {
                            diaryDetail.value = it
                        }
                    }
                })
        }

        return diaryDetail
    }

    fun submitDiary(diary: DiaryModel, picture: Bitmap?): LiveData<Boolean> {
        val db = firebaseHelper.getDiaryRef()
        var process = true

        try {
            launch {
                if (diary.diaryID == "") {
                    val diaryKey = db.child("diary-$userID").push().key
                    diary.apply {
                        this.diaryID = diaryKey!!
                        this.diaryType = "diary"
                    }
                } else if (picture != null) {
                    val oldImgRef = storageRef.child(diary.imgName)
                    oldImgRef.delete()
                        .addOnSuccessListener {
                            process = true
                        }
                        .addOnFailureListener {
                            process = false
                        }
                        .await()
                }

                if (process) {
                    picture?.let {
                        val uploadData = uploadImage(diary.diaryType, diary.diaryID, it)
                        diary.apply {
                            this.imgName = uploadData["imgName"].toString()
                            this.imgUrl = uploadData["imgUrl"].toString()
                        }
                    }

                    val update = HashMap<String, Any>()
                    update["/diary-$userID/${diary.diaryID}"] = diary
                    db.updateChildren(update)
                        .addOnCompleteListener { submitStatus.postValue(true) }
                        .addOnFailureListener { submitStatus.postValue(false) }
                } else {
                    submitStatus.postValue(false)
                }
            }
        } catch (e: Exception) {
            submitStatus.postValue(false)
        }

        return submitStatus
    }

    fun submitQuest(diary: DiaryModel, picture: Bitmap): LiveData<Boolean> {
        val db = firebaseHelper.getQuestRef()
        var process = true

        try {
            val userData = HashMap<String, String>()
            userData["userID"] = userID!!

            launch {
                if (diary.imgUrl != "") {
                    val oldImgRef = storageRef.child(diary.imgName)
                    oldImgRef.delete()
                        .addOnSuccessListener {
                            process = true
                        }
                        .addOnFailureListener {
                            process = false
                        }
                        .await()
                }

                if (process) {
                    val data = uploadImage(diary.diaryType, diary.diaryID, picture)
                    userData["imgName"] = data["imgName"].toString()
                    userData["imgUrl"] = data["imgUrl"].toString()
                    val update = HashMap<String, Any>()
                    update["/${diary.diaryID}/user/$userID"] = userData

                    db.updateChildren(update)
                        .addOnFailureListener { submitStatus.postValue(false) }
                        .addOnCompleteListener { submitStatus.postValue(true) }
                } else {
                    submitStatus.postValue(false)
                }
            }
        } catch (e: Exception) {
            submitStatus.postValue(false)
        }

        return submitStatus
    }

    fun deleteDiary(diary: DiaryModel): LiveData<Boolean> {
        try {
            val query = firebaseHelper.getDiaryRef()
                .child("diary-$userID/${diary.diaryID}")

            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Log.e(tag, "can not listen to database")
                    submitStatus.postValue(false)
                }

                override fun onDataChange(p0: DataSnapshot) {
                    p0.ref.removeValue()
                        .addOnCompleteListener {
                            val oldImgRef = storageRef.child(diary.imgName)
                            oldImgRef.delete()
                                .addOnSuccessListener {
                                    submitStatus.postValue(true)
                                }
                                .addOnFailureListener {
                                    submitStatus.postValue(false)
                                }
                        }
                        .addOnFailureListener {
                            submitStatus.postValue(false)
                        }
                }
            })
        } catch (e: Exception) {
            submitStatus.postValue(false)
        }

        return submitStatus
    }

    fun clearSubmitStatus() {
        submitStatus.value = null
    }

    private suspend fun uploadImage(
        type: String,
        name: String,
        picture: Bitmap
    ): HashMap<String, String> {
        val fileName = "${type}_${name}_$userID-${LocalDateTime.now()}.jpg"
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
