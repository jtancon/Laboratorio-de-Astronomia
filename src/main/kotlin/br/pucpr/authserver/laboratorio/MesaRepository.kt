package br.pucpr.authserver.laboratorio

import br.pucpr.authserver.pedidos.PedidoMesa
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import kotlin.collections.List

interface MesaRepository: JpaRepository<Mesa, Long> {

    fun findByNumeroMesa(numeroMesa: Int): Array<Mesa>

    @Query(value = "SELECT fechada FROM Mesa WHERE numeroMesa = :numeroMesa")
    fun findAbertaByNumeroMesa(@Param("numeroMesa") numeroMesa: Int): String?

    @Query(value = "SELECT MAX(id) FROM Mesa")
    fun findMaxId(): Long?

    @Query("""
    SELECT new br.pucpr.authserver.pedidos.PedidoMesa(
        m.numeroMesa,
        prod.descricao,
        p.quantidade
    )
    FROM Pedido p
    JOIN Produto prod ON p.codigoProduto = prod.codigoProduto
    JOIN Mesa m ON p.numeromesa = m.numeroMesa
    WHERE m.numeroMesa = :numeroMesa
""")
    fun findPedidosPorMesa(@Param("numeroMesa") numeroMesa: Int): List<PedidoMesa>

}