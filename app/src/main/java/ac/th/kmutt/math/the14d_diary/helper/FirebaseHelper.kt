package ac.th.kmutt.math.the14d_diary.helper

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseHelper{

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database = Firebase.database

    companion object{
        private var instance: FirebaseHelper? = null

        // singleton call
        fun getInstance(): FirebaseHelper{
            if (instance == null){
                instance = FirebaseHelper()
            }
            return instance as FirebaseHelper
        }
    }

    // authentication
    fun getCurrentUser(): FirebaseUser?{
        return mAuth.currentUser
    }

    // database

}