package br.pucpr.authserver

import br.pucpr.authserver.laboratorio.MesaRequest
import br.pucpr.authserver.users.User
import kotlin.random.Random

object Stubs {

    fun userStub(
        id: Long? = Random.nextLong(1, 1000),
        role: String? = "USER",
    ): User {
        val name = "User-${id?: "new"}"
        return User(
            id = id,
            name = name,
            email = "$name@email.com",
            password = "password",
            role = role,
        )
    }

    fun admStub(
        id: Long? = Random.nextLong(1, 1000),
        role: String? = "ADM",
    ): User {
        val name = "Adm-${id ?: "new"}"
        return User(
            id = id,
            name = name,
            email = "$name@email.com",
            password = "password",
            role = role,
        )
    }

    fun mesaStub(
        numeroMesa: Int = Random.nextInt(1, 1000)
    ): MesaRequest {
        return MesaRequest(
            numeroMesa = numeroMesa
        )
    }

}