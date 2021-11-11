package kr.co.softcampus.sopt_assignment1

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SigninService {

    @Headers("Content-Type: application/json")
    @POST("user/login")
    fun postLogin(
        @Body body: RequestSigninData
    ): Call<ResponseSigninData>
}