package kr.co.softcampus.sopt_assignment1.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kr.co.softcampus.sopt_assignment1.ui.activity.FindUserActivity
import kr.co.softcampus.sopt_assignment1.R
import kr.co.softcampus.sopt_assignment1.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        binding.btnFollower.isSelected = true

        initImage()
        initTransactionEvent()
        settingButton()

        return binding.root
    }

    private fun initImage() {
        Glide.with(this)
            .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRLor9UhA30HPuqs5FBmaHqoOzY2mt13wc1s7sOlmMhwxIhTMFqDNtIPdT_pi1kHTZJBN8&usqp=CAU")
            .circleCrop()
            .into(binding.ivPhoto)
    }

    private fun initTransactionEvent() {
        val followerFragment = FollowerFragment()
        val repositoryFragment = RepositoryFragment()

        getActivity()?.supportFragmentManager?.beginTransaction()
            ?.add(R.id.container_List, followerFragment)?.commit()
        //click event 발생할 경우
        btnRepository(repositoryFragment)
        btnFollower(followerFragment)
    }

    private fun btnRepository(repositoryFragment: Fragment) {
        binding.btnRepository.setOnClickListener {
            binding.btnFollower.isSelected = false
            binding.btnRepository.isSelected = true
            val transaction = getActivity()?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.container_List, repositoryFragment)
            transaction?.commit()
        }
    }

    private fun btnFollower(followerFragment: Fragment) {
        binding.btnFollower.setOnClickListener {
            binding.btnFollower.isSelected = true
            binding.btnRepository.isSelected = false
            val transaction = getActivity()?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.container_List, followerFragment)
            transaction?.commit()
        }
    }

    private fun settingButton() {
        binding.ivSetting.setOnClickListener {
            val intent_FindUser = Intent(getActivity(), FindUserActivity::class.java)
            startActivity(intent_FindUser)
        }
    }

}