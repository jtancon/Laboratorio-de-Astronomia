package br.pucpr.authserver.laboratorio

import br.pucpr.authserver.pedidos.PedidoLaboratorio
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import kotlin.collections.List

interface LaboratorioRepository : JpaRepository<Laboratorio, Long> {

    fun findByNumeroLaboratorio(numeroLaboratorio: Int): Array<Laboratorio>

    @Query(value = "SELECT fechada FROM Laboratorio WHERE numeroLaboratorio = :numeroLaboratorio")
    fun findAbertaByNumeroLaboratorio(@Param("numeroLaboratorio") numeroLaboratorio: Int): String?

    @Query(value = "SELECT MAX(id) FROM Laboratorio")
    fun findMaxId(): Long?

    @Query("""
        SELECT new br.pucpr.authserver.pedidos.PedidoLaboratorio(
            l.numeroLaboratorio,
            prod.descricao,
            p.quantidade
        )
        FROM Pedido p
        JOIN Produto prod ON p.codigoProduto = prod.codigoProduto
        JOIN Laboratorio l ON p.numeroLaboratorio = l.numeroLaboratorio
        WHERE l.numeroLaboratorio = :numeroLaboratorio
    """)
    fun findPedidosPorLaboratorio(@Param("numeroLaboratorio") numeroLaboratorio: Int): List<PedidoLaboratorio>
}
