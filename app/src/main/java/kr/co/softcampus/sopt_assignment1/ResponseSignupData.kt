package kr.co.softcampus.sopt_assignment1

data class ResponseSignupData(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : Data
){
    data class Data(
        val id : Int,
        val name : String,
        val email : String
    )
}
