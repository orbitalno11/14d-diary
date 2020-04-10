package ac.th.kmutt.math.the14d_diary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class StartScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)

        val intent = Intent(this, MainActivity::class.java)

        val runnable = Runnable {
            startActivity(intent)
            finish()
        }

        Handler().postDelayed(runnable, 1000)
    }
}
