package br.pucpr.authserver.produto

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

interface ProdutoRepository : JpaRepository<Produto, Long> {
    fun findByCodigoProduto(codigoProduto: Int): Produto?

    @Query("SELECT MAX(p.id) FROM Produto p")
    fun findMax(): Long?

    @Query("SELECT new br.pucpr.authserver.produto.ProdutoResponseBusca(p.codigoProduto, p.descricao, p.preco, p.codGruEst, c.descricao) FROM Produto p INNER JOIN CodGruEst c ON p.codGruEst = c.codigo ORDER BY c.descricao")
    fun findAllProdutos(): List<ProdutoResponseBusca>

    @Modifying
    @Transactional
    @Query("INSERT INTO tbl_produto (id, codigo_produto, descricao, preco, cod_gru_est) VALUES (:id, :codigoProduto, :descricao, :preco, :codGruEst)", nativeQuery = true)
    fun insertProduto(
        @Param("id") id: Long,
        @Param("codigoProduto") codigoProduto: Int,
        @Param("descricao") descricao: String,
        @Param("preco") preco: Double,
        @Param("codGruEst") codGruEst: Long
    )
}