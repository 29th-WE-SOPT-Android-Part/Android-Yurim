package kr.co.softcampus.sopt_assignment1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kr.co.softcampus.sopt_assignment1.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private var position = FOLLOWER_POSITION

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        gitClick()
        initTransactionEvent()
        setContentView(binding.root)
    }

    private fun gitClick(){
        binding.ibGit.setOnClickListener {
            val webPage = Uri.parse("https://github.com/ChoiYuLim")
            val intent = Intent(Intent.ACTION_VIEW, webPage)

            // 해당 intent를 성공적으로 수행할 수 있는지 체크
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Log.d("ImplicitIntents", "Can't handle this!")
            }
        }
    }

    private fun initTransactionEvent(){
        val followerFragment = FollowerFragment()
        val repositoryFragment = RepositoryFragment()

        supportFragmentManager.beginTransaction().add(R.id.container_List, followerFragment).commit()

        binding.btnRepository.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()

            when (position){
                FOLLOWER_POSITION -> {
                    transaction.replace(R.id.container_List,repositoryFragment)
                    position = REPOSITORY_POSITION
                }
            }
            transaction.commit()
        }

        binding.btnFollower.setOnClickListener{
            val transaction = supportFragmentManager.beginTransaction()

            when (position){
                REPOSITORY_POSITION -> {
                    transaction.replace(R.id.container_List, followerFragment)
                    position = FOLLOWER_POSITION
                }
            }
            transaction.commit()
        }
    }

    companion object {
        const val FOLLOWER_POSITION = 1
        const val REPOSITORY_POSITION = 2
    }

}