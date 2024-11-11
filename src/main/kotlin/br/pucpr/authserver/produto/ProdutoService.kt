package br.pucpr.authserver.produto

import br.pucpr.authserver.exceptions.NotFoundException
import br.pucpr.authserver.users.UsersService
import jakarta.transaction.Transactional
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
@Service
class ProdutoService (val repository: ProdutoRepository) {

    @Transactional
    fun saveProdut(produto: Produto) {
        val id = repository.findMax()?.plus(1) ?: 1
        repository.insertProduto(id, produto.codigoProduto, produto.descricao, produto.preco, produto.codGruEst)
        log.info("Produto descrição={} salvo com id={}", produto.descricao, id)
    }

    fun productGetById(id: Long) = repository.findById(id)

    fun getAllProdutos(): List<ProdutoResponseBusca> {
        return repository.findAllProdutos()
    }

    fun getProdutoByCodigo(codigoProduto: Int): Produto? {
        return repository.findByCodigoProduto(codigoProduto)
    }

    fun deleteProduct(id: Long) {
        log.info("Produto id={} deletado", id)
        repository.deleteById(id)
    }

    fun updateProduct(id: Long, updatedProduto: Produto): Produto {
        val existingProduto = repository.findById(id).orElseThrow { NotFoundException("Produto não encontrado") }
        log.info("Produto descrição={} encontrado, PReço: preco={}", existingProduto.descricao, existingProduto.preco)
        existingProduto.codigoProduto = updatedProduto.codigoProduto
        existingProduto.descricao = updatedProduto.descricao
        existingProduto.preco = updatedProduto.preco
        log.info("Produto descrição={} atualizado, Preco novo: preco={}", existingProduto.descricao, updatedProduto.preco)
        return repository.save(existingProduto)
    }

    companion object {
        val log = LoggerFactory.getLogger(ProdutoService::class.java)
    }

}

