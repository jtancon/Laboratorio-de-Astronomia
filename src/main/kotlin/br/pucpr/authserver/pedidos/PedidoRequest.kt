package br.pucpr.authserver.pedidos

import jakarta.validation.constraints.NotBlank


data class PedidoRequest(
    @field:NotBlank
    val codigoprodutos: Int,
    @field:NotBlank
    val numeromesa: Int,
    @field:NotBlank
    val quantidade: Int,
    val observacao: String? = null,
    @field:NotBlank
    val codgruest: Int,
)