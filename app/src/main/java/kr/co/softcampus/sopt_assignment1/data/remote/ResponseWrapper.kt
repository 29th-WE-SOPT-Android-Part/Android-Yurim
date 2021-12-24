package kr.co.softcampus.sopt_assignment1.data.remote

data class ResponseWrapper<T>(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: T?
)
