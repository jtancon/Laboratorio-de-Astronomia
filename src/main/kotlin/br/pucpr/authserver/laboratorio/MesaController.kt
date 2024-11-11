package br.pucpr.authserver.laboratorio

import br.pucpr.authserver.exceptions.BadRequestException
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/mesa")
class MesaController(val repository: MesaRepository) {

    @Operation(summary = "Abre uma mesa")
    @PostMapping("/{mesa}")
    fun saveMesa(@RequestBody @PathVariable mesa: Int): ResponseEntity<MesaResponse> {
        val id = repository.findMaxId()?.plus(1)
        val bacias = repository.findByNumeroMesa(mesa)
        var aberta = true
        bacias.map {
            if (it.numeroMesa == mesa) aberta = false
        }
        if (aberta) {
            val lindonjohnson = Mesa(id = id, numeroMesa = mesa, fechada = "N")
            val sim = repository.save(lindonjohnson)
            return ResponseEntity.status(CREATED).body(sim.toResponse())
        } else throw BadRequestException("Mesa já está aberta")
    }

    @Operation(summary = "Verifica todas as mesas")
    @GetMapping()
    fun findAll(): List<Mesa> = repository.findAll()

    @Operation(summary = "Verifica mesa por número")
    @GetMapping("/{numeroMesa}")
    fun findByNumeroMesa(@PathVariable numeroMesa: Int): Array<Mesa> = repository.findByNumeroMesa(numeroMesa)

    @Operation(summary = "Deleta a mesa")
    @DeleteMapping()
    fun deleteMesa(mesa: Mesa) = repository.delete(mesa)

    @Operation(summary = "Verifica os pedidos desta mesa")
    @GetMapping("/pedidos/{numeroMesa}")
    fun findPedidosPorMesa(@PathVariable("numeroMesa") numeroMesa: Int) = repository.findPedidosPorMesa(numeroMesa)

}
