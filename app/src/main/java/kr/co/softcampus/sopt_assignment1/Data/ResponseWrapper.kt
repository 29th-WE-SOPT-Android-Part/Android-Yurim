package kr.co.softcampus.sopt_assignment1.Data

data class ResponseWrapper<T>(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: T?
)
