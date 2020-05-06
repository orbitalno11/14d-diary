package ac.th.kmutt.math.the14d_diary

import ac.th.kmutt.math.the14d_diary.helper.FirebaseHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*


class Register : AppCompatActivity() {

    private val firebaseHelper = FirebaseHelper.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val userData = intent.getBundleExtra("LoginData")!!
        val displayName: String = userData.getString("name")!!
        val email: String = userData.getString("email")!!
        val picture: String = userData.getString("picture")!!

        Glide.with(this).load(picture).into(regis_pic)
        user_display_name.text = displayName
        user_email.text = email

        register_submit.setOnClickListener {
            val bundle = Bundle()
            bundle.putBundle("lineData", userData)
            firebaseHelper.getFirebaseCustomToken(this, bundle)
        }
    }
}
