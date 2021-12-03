package kr.co.softcampus.sopt_assignment1.Data

import com.google.gson.annotations.SerializedName

data class RequestSigninData(
    @SerializedName("email")
    val id: String,
    val password: String
)
