package br.pucpr.authserver.security

data class UserToken(

    val id: Long,
    val name: String,
    val role: Set<String>,

) {
    constructor(): this(0, "", setOf())
}
