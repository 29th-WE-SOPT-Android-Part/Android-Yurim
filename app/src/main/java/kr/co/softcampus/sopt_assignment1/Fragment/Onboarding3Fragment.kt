package kr.co.softcampus.sopt_assignment1.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kr.co.softcampus.sopt_assignment1.R
import kr.co.softcampus.sopt_assignment1.databinding.FragmentOnboarding3Binding

class Onboarding3Fragment : Fragment() {

    private var _binding: FragmentOnboarding3Binding? = null
    private val binding get() = _binding ?: error("Binding 이 초기화 되지 않았습니다.")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnboarding3Binding.inflate(layoutInflater, container, false)
        binding.btnStart.setOnClickListener {
            findNavController().navigate(R.id.action_onboarding3Fragment_to_signInActivity)
            requireActivity().finish()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}