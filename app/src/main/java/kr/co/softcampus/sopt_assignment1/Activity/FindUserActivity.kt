package kr.co.softcampus.sopt_assignment1.Activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kr.co.softcampus.sopt_assignment1.Data.ResponseGetUserByEmailData
import kr.co.softcampus.sopt_assignment1.Data.SharedPreferences
import kr.co.softcampus.sopt_assignment1.R
import kr.co.softcampus.sopt_assignment1.ServiceCreator
import kr.co.softcampus.sopt_assignment1.databinding.ActivityFindUserBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFindUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindUserBinding.inflate(layoutInflater)

        initcheckbox()
        checkboxClickEvent()
        SearchButton()

        setContentView(binding.root)
    }

    private fun initcheckbox() {
        if (SharedPreferences.getAutoLogin(this)) {
            binding.checkbox.isChecked = false
        } else binding.checkbox.isChecked = true
    }

    private fun checkboxClickEvent() {
        binding.checkbox.setOnClickListener {
            Toast.makeText(this, "자동 로그인 해제", Toast.LENGTH_SHORT).show()
            SharedPreferences.removeAutoLogin(this)
        }
    }

    private fun SearchButton() {
        binding.btnSearch.setOnClickListener {
            if (checkInputText()) {
                Toast.makeText(this, R.string.blank, Toast.LENGTH_SHORT).show()
            } else initNetwork()
        }
    }

    private fun checkInputText(): Boolean {
        return binding.etUseremail.text.isNullOrBlank()
    }

    private fun initNetwork() {

        val call: Call<ResponseGetUserByEmailData> =
            ServiceCreator.getUserByEmailService.getgetUserByEmail(binding.etUseremail.text.toString())

        call.enqueue(object : Callback<ResponseGetUserByEmailData> {
            override fun onResponse(
                call: Call<ResponseGetUserByEmailData>,
                response: Response<ResponseGetUserByEmailData>
            ) {
                if (response.isSuccessful) {
                    Log.d(
                        "success",
                        response.body()?.message + response.body()?.data?.email + response.body()?.data?.name
                    )
                    binding.tvResult.setText(response.body()?.message + "\n\nemail: " + response.body()?.data?.email + "\n\nname: " + response.body()?.data?.name)
                } else {
                    Log.d("fail", response.body()?.message.toString())
                    //binding.tvResult.setText(response.body()?.message + response.body()?.status + response.body()) //response 자체가 null값임
                    binding.tvResult.setText("존재하지 않는 회원입니다.")
                }
            }

            override fun onFailure(call: Call<ResponseGetUserByEmailData>, t: Throwable) {
                //에러 처리
                Toast.makeText(this@FindUserActivity, "ERROR", Toast.LENGTH_LONG).show()
                Log.e("NetworkTest", "error:$t")
            }
        })
    }
}