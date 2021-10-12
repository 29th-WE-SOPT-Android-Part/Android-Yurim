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
            if(binding.etId.length()!=0 && binding.etPw.length()!=0 && binding.etName.length()!=0) {
                val nextIntent = Intent(this,SignInActivity::class.java)
                nextIntent.putExtra("id", binding.etId.text.toString())
                nextIntent.putExtra("pw", binding.etPw.text.toString())
                setResult(RESULT_OK, nextIntent)
                finish()
            }
            else{
                Toast.makeText(this, "입력되지 않은 정보가 있습니다", Toast.LENGTH_SHORT).show()
            }
        }

        setContentView(binding.root)
    }

}
