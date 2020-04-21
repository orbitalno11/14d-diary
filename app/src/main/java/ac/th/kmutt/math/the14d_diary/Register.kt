package ac.th.kmutt.math.the14d_diary

import ac.th.kmutt.math.the14d_diary.helper.FirebaseHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_in.user_email
import kotlinx.android.synthetic.main.activity_sign_in.user_password

class Register : AppCompatActivity() {

    private val firebaseHelper = FirebaseHelper.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val mAuth = firebaseHelper.getAuth()

        register_submit.setOnClickListener {
            val email = user_email.text.toString().toLowerCase()
            val password = user_password.text.toString()
            val displayName = user_display_name.text.toString()
            val name = user_name.text.toString()
            val lastName = user_last_name.text.toString()

            if (email != null && password != null){
                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this){
                        if (it.isSuccessful){
                            val user = HashMap<String, String>()
                            user["displayName"] = displayName
                            user["name"] = name
                            user["lastName"] = lastName
                            user["email"] = email
                            val userID = mAuth.currentUser?.uid.toString()
                            val userRef = firebaseHelper.getUserRef()
                            userRef.child("/$userID").setValue(user)
                            finish()
                        }else{
                            Toast.makeText(applicationContext, "FAILED", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }
    }
}
