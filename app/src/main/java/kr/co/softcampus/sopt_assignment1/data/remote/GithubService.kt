package kr.co.softcampus.sopt_assignment1.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {
    @GET("/users/{username}/followers")
    fun getGithubUser(
        @Path("username") username: String
    ): Call<List<ResponseGithubData>>

    @GET("users/{username}")
    fun getUserInfo(
        @Path("username") username: String
    ) : Call<ResponseUserInfoData>
}