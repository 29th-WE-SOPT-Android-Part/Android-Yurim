package kr.co.softcampus.sopt_assignment1.Data

data class ResponseSigninData( //생성자 소괄호
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
