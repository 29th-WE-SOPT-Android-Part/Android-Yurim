package kr.co.softcampus.sopt_assignment1.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kr.co.softcampus.sopt_assignment1.Adapter.FollowerAdapter
import kr.co.softcampus.sopt_assignment1.Data.FollowerData
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
                FollowerData(
                    "박정훈",
                    "안드로이드",
                    "http://wiki.hash.kr/images/thumb/4/45/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C_%EB%A1%9C%EA%B3%A0.png/200px-%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C_%EB%A1%9C%EA%B3%A0.png"
                ),
                FollowerData(
                    "이준호",
                    "IOS",
                    "https://www.cctvnews.co.kr/news/photo/202005/202710_202771_3715.png"
                ),
                FollowerData(
                    "김인우",
                    "기획",
                    "https://thinkingpower.co.kr/wp-content/uploads/2019/07/%EC%95%84%EC%9D%B4%EB%94%94%EC%96%B4-768x767.jpg"
                ),
                FollowerData(
                    "박민우",
                    "안드로이드",
                    "http://wiki.hash.kr/images/thumb/4/45/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C_%EB%A1%9C%EA%B3%A0.png/200px-%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C_%EB%A1%9C%EA%B3%A0.png"
                ),
                FollowerData(
                    "김우영",
                    "서버",
                    "https://t1.daumcdn.net/cfile/tistory/99696A495C6BC05E0D"
                ),
                FollowerData(
                    "문다빈",
                    "안팟장",
                    "https://image.flaticon.com/icons/png/512/219/219683.png"
                ),
                FollowerData(
                    "한지우",
                    "디자인",
                    "https://mblogthumb-phinf.pstatic.net/MjAxOTA4MTFfNTcg/MDAxNTY1NDk5MDk1ODc2.NJJrChMjlZErPGfPPU41NH_U41dXE0928BznJsUIqXsg.bWohysoK1VPrm8nLgG-an7Oeh1VJmDkw4y8COxcUEtMg.PNG.as12345df/%EC%95%84%EC%9D%B4%ED%8C%A8%EB%93%9C+%ED%94%84%EB%A1%9C%ED%81%AC%EB%A6%AC%EC%97%90%EC%9D%B4%ED%8A%B8.png?type=w800"
                )
            )
        )
        followerAdapter.notifyDataSetChanged()
    }
}