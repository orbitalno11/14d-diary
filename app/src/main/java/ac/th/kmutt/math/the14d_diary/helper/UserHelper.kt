package ac.th.kmutt.math.the14d_diary.helper

import ac.th.kmutt.math.the14d_diary.model.UserModel
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class UserHelper : CoroutineScope {
    private val firebaseHelper = FirebaseHelper.getInstance()
    private val mAuth = FirebaseAuth.getInstance()
    private lateinit var lineHelper: LineHelper
    private val userData: MutableLiveData<UserModel> = MutableLiveData()
    private val tag = UserHelper::class.java.simpleName
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    init {
        this.fetchUserProfile()
    }

    private fun fetchUserProfile() {
        val userID = mAuth.currentUser?.uid
        userID?.let {
            firebaseHelper
                .getUserRef()
                .child(it)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        Log.e(tag, "Can not listen to database.")
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val data = p0.getValue(UserModel::class.java)!!
                        userData.value = data
                    }
                })
        } ?: run {
            Log.e(tag, "No User ID")
        }
    }

    fun getUserData(): UserModel? {
        return userData.value
    }

    suspend fun getUserLINEData(context: Context): Bundle = withContext(Dispatchers.IO) {
        lineHelper = LineHelper(context)
        if (lineHelper.verifyToken()){
            lineHelper.getProfile()
        }else {
            withContext(Dispatchers.Default) { lineHelper.refreshToken() }
            lineHelper.getProfile()
        }
    }
}