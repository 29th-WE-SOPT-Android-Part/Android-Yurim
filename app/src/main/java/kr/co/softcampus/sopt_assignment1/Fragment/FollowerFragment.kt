package kr.co.softcampus.sopt_assignment1.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kr.co.softcampus.sopt_assignment1.Adapter.FollowerAdapter
import kr.co.softcampus.sopt_assignment1.Data.FollowerData
import kr.co.softcampus.sopt_assignment1.Data.ResponseGithubData
import kr.co.softcampus.sopt_assignment1.Data.ResponseUserInfoData
import kr.co.softcampus.sopt_assignment1.ServiceCreator
import kr.co.softcampus.sopt_assignment1.databinding.FragmentFollowerBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerFragment : Fragment() {
    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")
    private lateinit var followerAdapter: FollowerAdapter
    private val _followerList = ArrayList<FollowerData>()
    val followerList: ArrayList<FollowerData>
        get() = _followerList

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowerBinding.inflate(layoutInflater, container, false)
        initAdapter()
        getGithubData()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initAdapter() {
        followerAdapter = FollowerAdapter()
        binding.rvFollower.adapter = followerAdapter

        //followerAdapter.notifyDataSetChanged()
    }

    fun getGithubData() {
        val call: Call<List<ResponseGithubData>> =
            ServiceCreator.githubService.getGithubUser("ChoiYuLim")

        call.enqueue(object : Callback<List<ResponseGithubData>> {
            override fun onResponse(
                call: Call<List<ResponseGithubData>>,
                response: Response<List<ResponseGithubData>>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        for (i in data) {
                            val login = i.login
                            val imgUrl = i.avatar_url

                            ServiceCreator.githubService
                                .getUserInfo(login)
                                .enqueue(object: Callback<ResponseUserInfoData>{
                                override fun onResponse(
                                    call: Call<ResponseUserInfoData>,
                                    response: Response<ResponseUserInfoData>
                                ) {
                                    if(response.isSuccessful){
                                        val newData = FollowerData(login, response.body()!!.bio, imgUrl)
                                        _followerList.add(newData)
                                        Log.d("성공이다", login+ imgUrl+response.body()!!.bio)
                                    }
                                    else{
                                    }
                                }

                                override fun onFailure(
                                    call: Call<ResponseUserInfoData>,
                                    t: Throwable
                                ) {
                                    Log.e("Network err", "error:$t")
                                }
                            })
                            
                        }
                        followerAdapter.followerList.addAll(_followerList)
                        followerAdapter.notifyDataSetChanged()
                    }
                }
                else{
                    Toast.makeText(context, "팔로워 리스트를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<ResponseGithubData>>, t: Throwable) {
                Log.e("Network err", "error:$t")
            }
        })
    }
}