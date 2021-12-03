package kr.co.softcampus.sopt_assignment1

import kr.co.softcampus.sopt_assignment1.Data.RequestSignupData
import kr.co.softcampus.sopt_assignment1.Data.ResponseSignupData
import kr.co.softcampus.sopt_assignment1.Data.ResponseWrapper
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignupService {
    @POST("user/signup")
    fun postSignup(
        @Body body: RequestSignupData
    ): Call<ResponseWrapper<ResponseSignupData>>
}