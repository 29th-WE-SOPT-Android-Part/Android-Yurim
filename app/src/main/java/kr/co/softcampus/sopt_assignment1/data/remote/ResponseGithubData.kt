package kr.co.softcampus.sopt_assignment1.data.remote

data class ResponseGithubData(
    val login: String,
    val avatar_url: String
)

data class ResponseUserInfoData(
    val bio: String?
)

