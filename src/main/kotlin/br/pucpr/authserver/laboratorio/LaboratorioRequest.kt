package br.pucpr.authserver.laboratorio

import jakarta.validation.constraints.NotNull

data class LaboratorioRequest (
    @field:NotNull
    val numeroLaboratorio: Int
)
