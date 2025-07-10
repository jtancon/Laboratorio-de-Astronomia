package br.pucpr.authserver.produto

import jakarta.validation.constraints.NotNull

class ProdutoRequest (
    @field:NotNull
    val codigoProduto:Int,
    @field:NotNull
    val descricao: String,
    @field:NotNull
    val preco: Double,
    @field:NotNull
    val codGruEst: Long
)