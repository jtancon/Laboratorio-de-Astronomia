package br.pucpr.authserver.pedidos

class PedidoResponse (
    val numeroPedido: Int,
    val numeromesa: Int,
    val quantidade: Int,
    val observacao: String? = null,
    val codGruEst: Int,
    val codigoProduto: Int,
)