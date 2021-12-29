package kr.co.softcampus.sopt_assignment1.data.remote

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SigninService {

    @POST("user/login")
    fun postLogin(
        @Body body: RequestSigninData
    ): Call<ResponseWrapper<ResponseSigninData>>
}