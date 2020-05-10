package ac.th.kmutt.math.the14d_diary

import ac.th.kmutt.math.the14d_diary.helper.UserHelper
import ac.th.kmutt.math.the14d_diary.model.UserModel
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var userHelper: UserHelper
    private val job = Job()

    private val REQUEST_CODE = 101

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val arr = arrayOf(
            android.Manifest.permission.CALL_PHONE,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
        requestPermission(arr, REQUEST_CODE)

        userHelper = UserHelper()

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_chat,
                R.id.nav_diary,
                R.id.nav_profile,
                R.id.nav_news,
                R.id.nav_diary,
                R.id.nav_profile
            ), app_drawer
        )

        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.fragment_host)
        navView.setupWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment_host)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun openDrawer() {
        app_drawer.openDrawer(GravityCompat.START)
    }

    private fun requestPermission(permissionType: Array<String>, requestCode: Int) {
        ActivityCompat.requestPermissions(this, permissionType, requestCode)
    }
}
