package br.pucpr.authserver.produto

import br.pucpr.authserver.pedidos.Pedido
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "tblProduto")
open class Produto(
    @Id @GeneratedValue
    var id: Long? = null,

    @Column(nullable = false)
    var codigoProduto: Int,

    @Column(nullable = false)
    var descricao: String,

    @Column(nullable = false)
    var preco: Double,

    @Column(nullable = false)
    var codGruEst: Long = 0,

) {
    fun toResponse() = ProdutoResponse(id, codigoProduto, descricao, preco, codGruEst)
}