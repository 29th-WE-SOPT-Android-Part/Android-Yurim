package kr.co.softcampus.sopt_assignment1.data.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {
    private const val BASE_URL = "https://asia-northeast3-we-sopt-29.cloudfunctions.net/api/"

    private val headerInterceptor = Interceptor{
        val request = it.request()
            .newBuilder()
            .addHeader("Content-Type","application/json")
            .build()
        return@Interceptor it.proceed(request)
    }

    val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(headerInterceptor)
        .build()

    private val retrofit: Retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val signinService: SigninService = retrofit.create(SigninService::class.java)
    val signupService: SignupService = retrofit.create(SignupService::class.java)
    val getUserByEmailService: GetUserByEmailService =
        retrofit.create(GetUserByEmailService::class.java)

    private const val GITHUB_BASE_URL = "https://api.github.com/"
    private val retrofit2: Retrofit = Retrofit
        .Builder()
        .baseUrl(GITHUB_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val githubService: GithubService = retrofit2.create(GithubService::class.java)
}