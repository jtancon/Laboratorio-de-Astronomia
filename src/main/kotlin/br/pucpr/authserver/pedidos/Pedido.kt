package br.pucpr.authserver.pedidos

import jakarta.persistence.*

@Entity
@Table(name = "tblPedidos")
open class Pedido(
    @Id @GeneratedValue
    var id: Int? = null,

    @Column(nullable = false)
    var numeroPedido: Int,

    @Column(nullable = false)
    var numeromesa: Int,

    @Column(nullable = false)
    var quantidade: Int,

    @Column(nullable = true)
    var observacao: String? = null,

    @Column(nullable = false)
    var codGruEst: Int,

    @Column(nullable = false)
    var codigoProduto: Int
) {
    fun toResponse() = PedidoResponse(numeroPedido, codGruEst, numeromesa, observacao, numeromesa, codigoProduto)
}