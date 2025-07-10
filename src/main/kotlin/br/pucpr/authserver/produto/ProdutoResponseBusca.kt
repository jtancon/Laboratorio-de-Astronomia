package br.pucpr.authserver.produto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class ProdutoResponseBusca(
    @field:NotNull
    val codigoProduto:Int,
    @field:NotBlank
    val descricao: String,
    @field:NotNull
    val preco: Double,
    @field:NotNull
    val cod: Long,
    @field:NotNull
    val codGruEst: String
)