package kr.co.softcampus.sopt_assignment1

import kr.co.softcampus.sopt_assignment1.Data.RequestSigninData
import kr.co.softcampus.sopt_assignment1.Data.ResponseSigninData
import kr.co.softcampus.sopt_assignment1.Data.ResponseWrapper
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SigninService {

    @POST("user/login")
    fun postLogin(
        @Body body: RequestSigninData
    ): Call<ResponseWrapper<ResponseSigninData>>
}