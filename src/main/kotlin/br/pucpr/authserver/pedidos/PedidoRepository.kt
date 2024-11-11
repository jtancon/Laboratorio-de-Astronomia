package br.pucpr.authserver.pedidos

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PedidoRepository : JpaRepository<Pedido, Int> {
    fun findByNumeroPedido(numeroPedido: Int): List<Pedido>

    @Query("SELECT MAX(p.numeroPedido) FROM Pedido p")
    fun findMaxNumeroPedido(): Int?
}