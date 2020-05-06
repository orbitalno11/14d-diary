package ac.th.kmutt.math.the14d_diary

import ac.th.kmutt.math.the14d_diary.`interface`.LineService
import ac.th.kmutt.math.the14d_diary.helper.FirebaseHelper
import ac.th.kmutt.math.the14d_diary.model.LineToken
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.linecorp.linesdk.LineApiResponseCode
import com.linecorp.linesdk.Scope
import com.linecorp.linesdk.api.LineApiClientBuilder
import com.linecorp.linesdk.auth.LineAuthenticationParams
import com.linecorp.linesdk.auth.LineLoginApi
import com.linecorp.linesdk.auth.LineLoginResult
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import kotlin.collections.HashMap

class SignIn : AppCompatActivity() {

    private val firebaseHelper = FirebaseHelper.getInstance()
    private val REQUEST_CODE_1: Int = 1
    private val REQUEST_CODE_2: Int = 2
    private val TAG: String = SignIn::class.java.simpleName
    private var isClicked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        line_login.setOnClickListener {
            isClicked = true

            val params: LineAuthenticationParams = LineAuthenticationParams.Builder()
                .scopes(listOf(Scope.OPENID_CONNECT, Scope.OC_EMAIL, Scope.PROFILE))
                .botPrompt(LineAuthenticationParams.BotPrompt.normal)
                .build()

            val loginIntent: Intent =
                LineLoginApi.getLoginIntent(this, Constants.LINE_CHANNEL_ID, params)
            startActivityForResult(loginIntent, REQUEST_CODE_1)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_1 -> {
                    Log.d("LOGIN", "SUCCESS CODE 1")
                    val result: LineLoginResult = LineLoginApi.getLoginResultFromIntent(data)
                    when (result.responseCode) {
                        LineApiResponseCode.SUCCESS -> {
                            val intent = Intent(this, Register::class.java)
                            val lineToken = LineToken()
                            val lineApiClient = LineApiClientBuilder(
                                applicationContext,
                                Constants.LINE_CHANNEL_ID
                            ).build()

                            lineToken.access_token =
                                lineApiClient.currentAccessToken.responseData.tokenString
                            val accessToken = lineToken.access_token
                            val picture = result.lineProfile?.pictureUrl.toString()
                            val name = result.lineProfile?.displayName
                            val email = result.lineIdToken?.email
                            val id = result.lineProfile?.userId

                            try {
                                val retrofit = Retrofit.Builder()
                                    .baseUrl(Constants.CLOUD_FUNCTION_API)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build()

                                val service = retrofit.create(LineService::class.java)
                                val params = HashMap<String, String>()
                                params["id"] = id!!

                                val call = service.checkUser(params)
                                call.enqueue(object : Callback<String> {
                                    override fun onFailure(call: Call<String>, t: Throwable) {
                                        Log.e(TAG, "Cannot check user.", t)
                                    }

                                    override fun onResponse(
                                        call: Call<String>,
                                        response: Response<String>
                                    ) {
                                        if (response.isSuccessful) {
                                            val responseBody = response.body()
                                            Log.d("CHECK USER", response.body().toString())
                                            responseBody?.let { body ->
                                                if (body.toBoolean()) {
                                                    Log.d("CHECK USER", "RESPONSE: ${response.body()}")
                                                    signIn()
                                                } else {
                                                    val bundle = Bundle()
                                                    bundle.putString("accessToken", accessToken)
                                                    bundle.putString("email", email)
                                                    bundle.putString("picture", picture)
                                                    bundle.putString("name", name)
                                                    bundle.putString("id", id)
                                                    intent.putExtra("LoginData", bundle)
                                                    startActivity(intent)
                                                    finish()
                                                }
                                            } ?: also {
                                                Log.e(TAG, "Response body is null.")
                                            }
                                        } else {
                                            Log.d("CHECK USER", "RESPONSE: ${response.body()}")
                                        }
                                    }
                                })
                            } catch (e: Exception) {
                                Log.e("CHECK USER", "EX $e")
                            }
                            Log.d("CHECK USER", "SHOULD BACK")
                        }
                        LineApiResponseCode.CANCEL -> {
                            isClicked = false
                            Log.e("Line Login", "LINE Login was cancelled by user!")
                        }
                        else -> {
                            isClicked = false
                            Log.e("Line Login", result.errorData.toString())
                        }
                    }
                }
                REQUEST_CODE_2 -> {
                    Log.d("Line Login", "WEB!")
                }
            }
        }else{
            Log.d("Line Login", "ACTIVITY CANCEL")
            val result: LineLoginResult = LineLoginApi.getLoginResultFromIntent(data)
            println(result)
        }
    }

    private fun signIn() {
        CoroutineScope(IO).launch {
            val token = async {
                firebaseHelper.getCustomToken(applicationContext)
            }.await()

            val mAuth = firebaseHelper.getAuth()
            mAuth.signInWithCustomToken(token.firebase_token)
                .addOnCompleteListener(this@SignIn) {
                    if (it.isSuccessful) {
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                        finish()
                    }
                }
        }
    }
}
