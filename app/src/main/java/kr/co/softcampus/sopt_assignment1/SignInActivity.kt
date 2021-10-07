package kr.co.softcampus.sopt_assignment1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kr.co.softcampus.sopt_assignment1.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)

        val intent = Intent(this, HomeActivity::class.java)

        binding.btnLogin.setOnClickListener {
            if(binding.etId.length()!=0 && binding.etPw.length()!=0) {
                Toast.makeText(this, binding.etId.text.toString()+"님 환영합니다", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
            }
            binding.etId.setText(null)
            binding.etPw.setText(null)
        }

        val intent2 = Intent(this,SignUpActivity::class.java)
        binding.btnReg.setOnClickListener{
            startActivity(intent2)
            binding.etId.setText(null)
            binding.etPw.setText(null)
        }

        setContentView(binding.root)
    }
}