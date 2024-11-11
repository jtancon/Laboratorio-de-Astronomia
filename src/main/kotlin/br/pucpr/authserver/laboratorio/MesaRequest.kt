package br.pucpr.authserver.laboratorio

import jakarta.validation.constraints.NotNull

data class MesaRequest (
    @field:NotNull
    val numeroMesa:Int
)