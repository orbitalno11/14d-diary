package ac.th.kmutt.math.the14d_diary.ui.profile

import ac.th.kmutt.math.the14d_diary.MainActivity
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ac.th.kmutt.math.the14d_diary.R
import ac.th.kmutt.math.the14d_diary.SignIn
import ac.th.kmutt.math.the14d_diary.helper.AppbarHelper
import ac.th.kmutt.math.the14d_diary.helper.LineHelper
import ac.th.kmutt.math.the14d_diary.helper.UserHelper
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ProfileFragment : Fragment(), CoroutineScope {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private val mAuth = FirebaseAuth.getInstance()
    private lateinit var userHelper: UserHelper
    private lateinit var appbarHelper: AppbarHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (mAuth.currentUser == null) {
            startActivity(Intent(context, SignIn::class.java))
            activity?.finish()
            null
        } else {
            inflater.inflate(R.layout.fragment_profile, container, false)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        view?.let {
            setupAppbar()
            userHelper = UserHelper()
            launch {
                val userData = userHelper.getUserLINEData(activity?.applicationContext!!)
                val picture = userData.getString("picture")!!
                val name = userData.getString("displayName")

                Glide.with(this@ProfileFragment).load(picture).centerInside().circleCrop().into(profile_picture)
                profile_user_name.text = name

                log_out.setOnClickListener {
                    mAuth.signOut()
                    val lineHelper = LineHelper(activity?.applicationContext!!)
                    launch {
                        lineHelper.logout()
                    }
                    startActivity(Intent(activity!!.applicationContext, MainActivity::class.java))
                    activity!!.finish()
                }
            }
        }
    }

    private fun setupAppbar(){
        view?.let {
            appbarHelper = AppbarHelper(it)
            appbarHelper.setTitle(resources.getString(R.string.nav_profile))
            appbarHelper.setMenuNav(activity as MainActivity)
        }
    }

}
