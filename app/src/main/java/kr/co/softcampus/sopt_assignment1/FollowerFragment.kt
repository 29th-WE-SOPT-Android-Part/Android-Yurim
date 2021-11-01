package kr.co.softcampus.sopt_assignment1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kr.co.softcampus.sopt_assignment1.databinding.FragmentFollowerBinding

class FollowerFragment : Fragment() {
    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")
    private lateinit var followerAdapter: FollowerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowerBinding.inflate(layoutInflater, container, false)
        initAdapter()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initAdapter() {

        followerAdapter = FollowerAdapter()
        binding.rvFollower.adapter = followerAdapter
        followerAdapter.followerList.addAll(
            listOf(
                FollowerData("박정훈", "안드로이드"),
                FollowerData("이준호", "IOS"),
                FollowerData("김인우", "기획"),
                FollowerData("박민우", "안드로이드"),
                FollowerData("김우영", "서버"),
                FollowerData("문다빈", "안팟장"),
                FollowerData("한지우","디자인")
            )
        )
        followerAdapter.notifyDataSetChanged()
    }
}