package kr.co.softcampus.sopt_assignment1

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.softcampus.sopt_assignment1.databinding.FragmentFollowerBinding

class FollowerFragment : Fragment() {
    private var _binding : FragmentFollowerBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")
    private lateinit var followerAdapter : FollowerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowerBinding.inflate(layoutInflater, container,false)
        initAdapter()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding= null
    }
    private fun initAdapter() {

        followerAdapter = FollowerAdapter()
        binding.rvFollower.adapter = followerAdapter
        binding.rvFollower.addItemDecoration(ItemDecoration(10, Color.rgb(255,51,153)))
        followerAdapter.followerList.addAll(
            listOf(
                FollowerData(R.drawable.lim,"최유림", "안드로이드"),
                FollowerData(R.drawable.one,"박정훈", "안드로이드"),
                FollowerData(R.drawable.one,"이준호", "IOS"),
                FollowerData(R.drawable.one,"김인우", "기획"),
                FollowerData(R.drawable.one,"박민우", "안드로이드"),
                FollowerData(R.drawable.one,"김우영", "서버")
            )
        )
        followerAdapter.notifyDataSetChanged()
    }
}