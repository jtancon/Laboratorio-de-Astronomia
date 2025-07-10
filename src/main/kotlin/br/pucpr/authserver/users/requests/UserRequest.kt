package br.pucpr.authserver.users.requests

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class UserRequest(
    @field:NotBlank
    val name: String?,
    @field:Email
    val email: String,
    @field:Size(min = 4, max = 20)
    val password: String,
    @field:Pattern(regexp = "^(USER|ADM)$")
    val role: String?
)
