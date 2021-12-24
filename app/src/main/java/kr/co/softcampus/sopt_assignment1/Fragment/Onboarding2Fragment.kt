package kr.co.softcampus.sopt_assignment1.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kr.co.softcampus.sopt_assignment1.R
import kr.co.softcampus.sopt_assignment1.databinding.FragmentOnboarding2Binding

class Onboarding2Fragment : Fragment() {

    private var _binding: FragmentOnboarding2Binding? = null
    private val binding get() = _binding ?: error("Binding 이 초기화 되지 않았습니다.")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnboarding2Binding.inflate(layoutInflater, container, false)
        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_onboarding2Fragment_to_onboarding3Fragment)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}