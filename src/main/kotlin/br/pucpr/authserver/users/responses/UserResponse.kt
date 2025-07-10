package br.pucpr.authserver.users.responses

data class UserResponse(
    val id: Long,
    val name: String,
    val email: String,
    val role: String?
)
