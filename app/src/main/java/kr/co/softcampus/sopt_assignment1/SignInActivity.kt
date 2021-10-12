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

        val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result->
            if(result.resultCode == Activity.RESULT_OK){
                val id = result.data?.getStringExtra("id")
                val pw = result.data?.getStringExtra("pw")
                binding.etId.setText(id)
                binding.etPw.setText(pw)
            }
        }

        val intent = Intent(this, HomeActivity::class.java)
        binding.btnLogin.setOnClickListener {
            if(binding.etId.length()!=0 && binding.etPw.length()!=0) {
                Toast.makeText(this, binding.etId.text.toString()+"님 환영합니다", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }
            else{Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()}
        }

        val intent2 = Intent(this,SignUpActivity::class.java)
        binding.btnReg.setOnClickListener{
            startForResult.launch(intent2)
        }

        setContentView(binding.root)
    }
}