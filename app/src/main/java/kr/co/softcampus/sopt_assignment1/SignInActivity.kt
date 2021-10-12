package kr.co.softcampus.sopt_assignment1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kr.co.softcampus.sopt_assignment1.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)

        Login()
        Register()

        setContentView(binding.root)
    }

    private fun Login() {
        binding.btnLogin.setOnClickListener {
            if (binding.etId.text.isNullOrBlank() || binding.etPw.text.isNullOrBlank()) {
                Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
            } else {
                val intent_Home = Intent(this, HomeActivity::class.java)
                Toast.makeText(this, "${binding.etId.text}" + "님 환영합니다", Toast.LENGTH_SHORT).show()
                startActivity(intent_Home)
            }
        }
    }

    private fun Register() {
        val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result->
            if(result.resultCode == Activity.RESULT_OK){
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