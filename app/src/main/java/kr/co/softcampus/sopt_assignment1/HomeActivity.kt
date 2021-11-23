package kr.co.softcampus.sopt_assignment1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kr.co.softcampus.sopt_assignment1.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        val intro = Introduce(
            "최유림",
            24,
            "esfp",
            "안녕하세요 SOPT 29기 \n안드로이드 YB입니다\n잘 부탁드립니다~♥\n\n안녕하세요 SOPT 29기 \n안드로이드 YB입니다\n잘 부탁드립니다~♥\n\n안녕하세요 SOPT 29기 \n안드로이드 YB입니다\n잘 부탁드립니다~♥\n\n안녕하세요 SOPT 29기 \n안드로이드 YB입니다\n잘 부탁드립니다~♥\n\n안녕하세요 SOPT 29기 \n안드로이드 YB입니다\n잘 부탁드립니다~♥",
            R.drawable.lim
        )

        binding.introduce = intro

        binding.git.setOnClickListener {
            val webPage: Uri = Uri.parse("https://github.com/ChoiYuLim")
            val intent = Intent(Intent.ACTION_VIEW, webPage)

            // 해당 intent를 성공적으로 수행할 수 있는지 체크
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Log.d("ImplicitIntents", "Can't handle this!")
            }
        }
        setContentView(binding.root)
    }
}