package ac.th.kmutt.math.the14d_diary

import ac.th.kmutt.math.the14d_diary.helper.FirebaseHelper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignIn : AppCompatActivity() {

    private val firebaseHelper = FirebaseHelper.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        register_btn.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
            finish()
        }

        sign_in_submit.setOnClickListener {
            val email = user_email.text.toString().toLowerCase()
            val password = user_password.text.toString().toLowerCase()
            val mAuth = firebaseHelper.getAuth()

            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {
                    if (it.isSuccessful){
                        finish()
                    }else{
                        Toast.makeText(applicationContext, "FAILED ${it.exception}", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}
