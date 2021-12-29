package kr.co.softcampus.sopt_assignment1.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetUserByEmailService {
    @GET("user")
    fun getgetUserByEmail(@Query("email") email: String): Call<ResponseGetUserByEmailData>
}
