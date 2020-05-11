package ac.th.kmutt.math.the14d_diary

import ac.th.kmutt.math.the14d_diary.fragment.LoadingDialog
import ac.th.kmutt.math.the14d_diary.helper.FirebaseHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*


class Register : AppCompatActivity() {

    private val firebaseHelper = FirebaseHelper.getInstance()
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setupLoadingDialog()

        val userData = intent.getBundleExtra("LoginData")!!
        val displayName: String = userData.getString("name")!!
        val email: String = userData.getString("email")!!
        val picture: String = userData.getString("picture")!!

        Glide.with(this).load(picture).into(regis_pic)
        user_display_name.text = displayName
        user_email.text = email

        register_submit.setOnClickListener {
            openLoadingDialog()
            val bundle = Bundle()
            bundle.putBundle("lineData", userData)
            firebaseHelper.getFirebaseCustomToken(this, bundle)
            closeLoadingDialog()
        }
    }

    private fun setupLoadingDialog(){
        this.loadingDialog = LoadingDialog.Builder()
            .setTitle("กรุณารอสักครู่")
            .setMessage("กำลังตรวจสอบข้อมูล")
            .build()
    }

    private fun openLoadingDialog(){
        this.loadingDialog.show(this.supportFragmentManager, "LOAD")
    }

    private fun closeLoadingDialog(){
        this.loadingDialog.dismiss()
    }
}
