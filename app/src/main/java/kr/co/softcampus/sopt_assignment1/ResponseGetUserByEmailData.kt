package kr.co.softcampus.sopt_assignment1

data class ResponseGetUserByEmailData(
    val status: Int,
    val sucess: Boolean,
    val message: String,
    val data: Data
) {
    data class Data(
        val id: Int,
        val name: String,
        val email: String
    )
}
