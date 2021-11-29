package kr.co.softcampus.sopt_assignment1

import kr.co.softcampus.sopt_assignment1.Data.ResponseGetUserByEmailData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GetUserByEmailService {
    @Headers("Content-Type: application/json")
    @GET("user")
    fun getgetUserByEmail(@Query("email") email: String): Call<ResponseGetUserByEmailData>
}
