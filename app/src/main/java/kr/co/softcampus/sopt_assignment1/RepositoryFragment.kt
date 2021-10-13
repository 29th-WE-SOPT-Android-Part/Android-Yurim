package kr.co.softcampus.sopt_assignment1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.softcampus.sopt_assignment1.databinding.FragmentRepositoryBinding

class RepositoryFragment : Fragment() {

    private var _binding: FragmentRepositoryBinding? = null
    private val binding get() = _binding ?:error("Binding이 초기화되지 않았습니다.")
    private lateinit var repositoryAdapter: RepositoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRepositoryBinding.inflate(layoutInflater,container,false)
        initAdapter()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun initAdapter() {

        repositoryAdapter = RepositoryAdapter()
        binding.rvRepository.adapter = repositoryAdapter
        repositoryAdapter.RepositoryList.addAll(
            listOf(
                RepositoryData("안드로이드 과제 레포지토리", "안드로이드 파트 과제"),
                RepositoryData("ios 과제 레포지토리", "ios 파트 과제"),
                RepositoryData("서버 과제 레포지토리", "서버 파트 과제"),
                RepositoryData("기획 과제 레포지토리", "기획 파트 과제"),
                RepositoryData("SOPT 과제 레포지토리","안드로이드 파트 과제, ios 파트 과제, 서버 파트 과제, 기획 파트 과제, 웹 파트 과제, 디자인 파트 과제")
            )
        )
        repositoryAdapter.notifyDataSetChanged()
    }

}