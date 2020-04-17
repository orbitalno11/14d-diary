package ac.th.kmutt.math.the14d_diary

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_appbar.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
//        setSupportActionBar(toolbar)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_chat, R.id.nav_diary, R.id.nav_profile, R.id.nav_news, R.id.nav_diary
            ), app_drawer
        )

        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.fragment_host)
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
//        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment_host)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun openDrawer(){
        app_drawer.openDrawer(GravityCompat.START)
    }
}
