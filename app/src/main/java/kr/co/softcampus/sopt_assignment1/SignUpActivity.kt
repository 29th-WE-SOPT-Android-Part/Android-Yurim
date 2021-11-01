package kr.co.softcampus.sopt_assignment1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kr.co.softcampus.sopt_assignment1.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)



        binding.btnReg.setOnClickListener {
            if (binding.etId.text.isNullOrBlank() || binding.etPw.text.isNullOrBlank() || binding.etName.text.isNullOrBlank()) {
                Toast.makeText(this, "입력되지 않은 정보가 있습니다", Toast.LENGTH_SHORT).show()
            } else {
                val Intent_SignIn = Intent(this, SignInActivity::class.java)
                Intent_SignIn.putExtra("id", binding.etId.text.toString())
                Intent_SignIn.putExtra("pw", binding.etPw.text.toString())
                setResult(RESULT_OK, Intent_SignIn)
                finish()
            }
        }

        setContentView(binding.root)
    }

}
