package kr.co.softcampus.sopt_assignment1.data.remote

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignupService {
    @POST("user/signup")
    fun postSignup(
        @Body body: RequestSignupData
    ): Call<ResponseWrapper<ResponseSignupData>>
}