package kr.co.softcampus.sopt_assignment1.data.remote

data class ResponseGetUserByEmailData(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: Data
) {
    data class Data(
        val id: Int,
        val name: String,
        val email: String
    )
}
