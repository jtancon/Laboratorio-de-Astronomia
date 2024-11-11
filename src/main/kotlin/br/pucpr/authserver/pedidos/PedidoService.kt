package br.pucpr.authserver.pedidos

import org.springframework.stereotype.Service

@Service
class PedidoService(val repository: PedidoRepository) {

    fun listaPedidos(numeroPedido: Int) = repository.findByNumeroPedido(numeroPedido)

    fun save(pedido: Pedido) = repository.save(pedido)

    fun findMax(): Int {
        return repository.findMaxNumeroPedido() ?: 1
    }
}