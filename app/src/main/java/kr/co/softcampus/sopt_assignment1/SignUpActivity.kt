package kr.co.softcampus.sopt_assignment1

import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kr.co.softcampus.sopt_assignment1.databinding.ActivitySignInBinding
import kr.co.softcampus.sopt_assignment1.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)

        binding.btnReg.setOnClickListener {
            if(binding.etId.length()!=0 && binding.etPw.length()!=0 && binding.etName.length()!=0) {
                finish()
            }
            else{
                Toast.makeText(this, "입력되지 않은 정보가 있습니다", Toast.LENGTH_SHORT).show()
            }
        }

        setContentView(binding.root)
    }

}
