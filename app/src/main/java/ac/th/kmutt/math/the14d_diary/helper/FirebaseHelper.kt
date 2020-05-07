package ac.th.kmutt.math.the14d_diary.helper

import ac.th.kmutt.math.the14d_diary.Constants
import ac.th.kmutt.math.the14d_diary.MainActivity
import ac.th.kmutt.math.the14d_diary.Register
import ac.th.kmutt.math.the14d_diary.`interface`.LineService
import ac.th.kmutt.math.the14d_diary.model.FirebaseCustomToken
import ac.th.kmutt.math.the14d_diary.model.UserModel
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.linecorp.linesdk.api.LineApiClientBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FirebaseHelper {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database = Firebase.database

    companion object {
        private var instance: FirebaseHelper? = null

        // singleton call
        fun getInstance(): FirebaseHelper {
            if (instance == null) {
                instance = FirebaseHelper()
            }
            return instance as FirebaseHelper
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.CLOUD_FUNCTION_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // authentication
    fun getCurrentUser(): FirebaseUser? {
        return mAuth.currentUser
    }

    fun getAuth(): FirebaseAuth {
        return mAuth
    }

    // database
    fun getDatabaseRef(): DatabaseReference {
        return database.reference
    }

    // get user database
    fun getUserRef(): DatabaseReference {
        return database.getReference("user")
    }

    // get message database
    fun getMessageDatabaseRef(): DatabaseReference{
        return database.getReference("chat")
    }

    fun createUser(data: Bundle) {
        val user = UserModel()
        user.apply {
            this.displayName = data.getString("name")!!
            this.email = data.getString("email")!!
            this.userID = "line:${data.getString("id")}"
            this.picture = data.getString("picture")!!
        }
        val userID = "line:${data.getString("id")}"
        getUserRef().child(userID).setValue(user)
    }


    suspend fun getCustomToken(applicationContext: Context): FirebaseCustomToken {
        val lineApiClient =
            LineApiClientBuilder(applicationContext, Constants.LINE_CHANNEL_ID).build()
        val profile = lineApiClient.profile.responseData
        // create retrofit
        val retrofit = getRetrofit()
        val service = retrofit.create(LineService::class.java)
        // create parameter
        val params = HashMap<String, String>()
        params["id"] = profile.userId
        return service.getMemberToken(params)
    }

    fun getFirebaseCustomToken(activity: Register, bundle: Bundle) {
        // create retrofit
        val retrofit = getRetrofit()
        val service = retrofit.create(LineService::class.java)

        // get data from sign up activity
        val lineData = bundle.getBundle("lineData")

        // set up parameter
        val params = HashMap<String, String>()
        params["access_token"] = lineData!!.getString("accessToken")!!
        params["picture"] = lineData.getString("picture")!!
        params["email"] = lineData.getString("email")!!
        params["name"] = lineData.getString("name")!!
        params["id"] = lineData.getString("id")!!

        val call = service.createCustomToken(params)
        call.enqueue(object : Callback<FirebaseCustomToken> {
            override fun onFailure(call: Call<FirebaseCustomToken>, t: Throwable) {
                Toast.makeText(
                    activity.applicationContext,
                    "LINE ERROR ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onResponse(
                call: Call<FirebaseCustomToken>,
                response: Response<FirebaseCustomToken>
            ) {
                if (response.isSuccessful) {
                    // can get custom token
                    response.body()?.let { firebaseCustomToken ->
                        val firebaseToken = firebaseCustomToken.firebase_token
                        mAuth.signInWithCustomToken(firebaseToken)
                            .addOnCompleteListener { auth ->
                                if (auth.isSuccessful) {
                                    createUser(lineData)
                                    lineData?.let {
                                        activity.startActivity(
                                            Intent(
                                                activity,
                                                MainActivity::class.java
                                            )
                                        )
                                        activity.finish()
                                    } ?: run {
                                        Toast.makeText(
                                            activity.applicationContext,
                                            "LINE ERROR ${auth.exception!!.message}",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                } else {
                                    Toast.makeText(
                                        activity.applicationContext,
                                        "LINE ERROR ${auth.exception!!.message}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                    } ?: also {
                        println("TOKEN is null")
                        Toast.makeText(
                            activity.applicationContext,
                            "LINE ERROR",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    println("RESPONSE IS ERROR")
                    println(response.body().toString())
                    Toast.makeText(
                        activity.applicationContext,
                        "LINE ERROR RESPONSE IS ERROR",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        })
    }
}