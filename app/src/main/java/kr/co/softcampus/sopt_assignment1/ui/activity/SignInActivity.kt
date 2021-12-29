package kr.co.softcampus.sopt_assignment1.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kr.co.softcampus.sopt_assignment1.data.remote.RequestSigninData
import kr.co.softcampus.sopt_assignment1.data.remote.ResponseSigninData
import kr.co.softcampus.sopt_assignment1.data.remote.ResponseWrapper
import kr.co.softcampus.sopt_assignment1.util.SharedPreferences
import kr.co.softcampus.sopt_assignment1.R
import kr.co.softcampus.sopt_assignment1.data.remote.ServiceCreator
import kr.co.softcampus.sopt_assignment1.databinding.ActivitySignInBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)

        initClickEvent()
        isAutoLogin()
        LoginButton()
        RegisterButton()

        setContentView(binding.root)
    }

    private fun initClickEvent() {
        binding.ibCheck.setOnClickListener {
            binding.ibCheck.isSelected = !binding.ibCheck.isSelected
            SharedPreferences.setAutoLogin(this, binding.ibCheck.isSelected)
        }
    }

    private fun isAutoLogin(){
        if(SharedPreferences.getAutoLogin(this)){
            Toast.makeText(this@SignInActivity, "자동로그인 되었습니다.", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@SignInActivity, HomeActivity::class.java))
            finish()
        }
    }

    private fun LoginButton() {
        binding.btnLogin.setOnClickListener {
            if (checkInputText()) {
                Toast.makeText(this, R.string.blank, Toast.LENGTH_SHORT).show()
            } else initNetwork()
        }
    }

    private fun initNetwork() {
        val requestSigninData = RequestSigninData(
            id = binding.etId.text.toString(),
            password = binding.etPw.text.toString()
        )

        val call: Call<ResponseWrapper<ResponseSigninData>> =
            ServiceCreator.signinService.postLogin(requestSigninData)

        call.enqueue(object : Callback<ResponseWrapper<ResponseSigninData>> {
            override fun onResponse(
                call: Call<ResponseWrapper<ResponseSigninData>>,
                response: Response<ResponseWrapper<ResponseSigninData>>
            ) {
                if (response.isSuccessful) {
                    successLogin(response.body()?.data?.name)
                } else {
                    Toast.makeText(this@SignInActivity, R.string.fail_login, Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<ResponseWrapper<ResponseSigninData>>, t: Throwable) {
                //에러 처리
                Toast.makeText(this@SignInActivity, "ERROR", Toast.LENGTH_LONG).show()
                Log.e("NetworkTest", "error:$t")

            }
        })

    }

    private fun checkInputText(): Boolean {
        return binding.etId.text.isNullOrBlank() || binding.etPw.text.isNullOrBlank()
    }

    private fun successLogin(name: String?) {
        val intent_Home = Intent(this, HomeActivity::class.java)
        Toast.makeText(this, name + "님 환영합니다", Toast.LENGTH_SHORT).show()
        startActivity(intent_Home)
    }

    private fun RegisterButton() {
        val startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val id = result.data?.getStringExtra("id")
                    val pw = result.data?.getStringExtra("pw")
                    binding.etId.setText(id)
                    binding.etPw.setText(pw)
                }
            }
        binding.btnReg.setOnClickListener {
            val intent_SignUp = Intent(this, SignUpActivity::class.java)
            startForResult.launch(intent_SignUp)
        }
    }
}