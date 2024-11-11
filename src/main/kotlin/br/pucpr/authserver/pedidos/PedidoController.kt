package br.pucpr.authserver.pedidos

import br.pucpr.authserver.produto.ProdutoService
import br.pucpr.authserver.exceptions.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/pedido")
class PedidoController(private val service: PedidoService, private val produtoService: ProdutoService) {

    @GetMapping("/{pedido}")
    fun listaPedidos(@RequestParam("numeroPedido") pedido: Int) =
        service.listaPedidos(pedido)

    @PostMapping()
    fun savePedido(@RequestBody reqList: List<PedidoRequest>): ResponseEntity<List<PedidoResponse>> {
        val numpedido = service.findMax()
        val pedidos = reqList.map { req ->
            Pedido(
                numeroPedido = numpedido,
                numeromesa = req.numeromesa,
                quantidade = req.quantidade,
                observacao = req.observacao,
                codGruEst = req.codgruest,
                codigoProduto = req.codigoprodutos
            )
        }
        val savedPedidos = pedidos.map { service.save(it) }
        val response = savedPedidos.map { it.toResponse() }
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }
}