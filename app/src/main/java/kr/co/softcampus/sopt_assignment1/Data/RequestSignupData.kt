package kr.co.softcampus.sopt_assignment1.Data

import com.google.gson.annotations.SerializedName

data class RequestSignupData(
    @SerializedName("email")
    val id: String,
    val name: String,
    val password: String
)
