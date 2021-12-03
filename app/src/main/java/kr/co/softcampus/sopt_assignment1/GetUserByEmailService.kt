package kr.co.softcampus.sopt_assignment1

import kr.co.softcampus.sopt_assignment1.Data.ResponseGetUserByEmailData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetUserByEmailService {
    @GET("user")
    fun getgetUserByEmail(@Query("email") email: String): Call<ResponseGetUserByEmailData>
}
