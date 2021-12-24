package kr.co.softcampus.sopt_assignment1.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kr.co.softcampus.sopt_assignment1.R
import kr.co.softcampus.sopt_assignment1.databinding.FragmentOnboarding1Binding

class Onboarding1Fragment : Fragment() {
    private var _binding: FragmentOnboarding1Binding? = null
    private val binding get() = _binding ?: error("Binding 이 초기화 되지 않았습니다.")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnboarding1Binding.inflate(layoutInflater,container,false)
        binding.btnNext.setOnClickListener{
            findNavController().navigate(R.id.action_onboarding1Fragment_to_onboarding2Fragment)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}