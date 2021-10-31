package kr.co.softcampus.sopt_assignment1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kr.co.softcampus.sopt_assignment1.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
    }

    private fun initData(){
        binding.ivPhoto.setImageResource(intent.getIntExtra("img", 0))
        binding.tvName2.setText(intent.getStringExtra("name"))
        binding.tvDetail2.setText(intent.getStringExtra("detail"))
    }
}

