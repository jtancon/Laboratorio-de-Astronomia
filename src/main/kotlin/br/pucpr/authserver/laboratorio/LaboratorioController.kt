package br.pucpr.authserver.laboratorio

import br.pucpr.authserver.exceptions.BadRequestException
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/laboratorio")
class LaboratorioController(val repository: LaboratorioRepository) {

    @Operation(summary = "Abre um laboratório")
    @PostMapping("/{numeroLaboratorio}")
    fun saveLaboratorio(@PathVariable numeroLaboratorio: Int): ResponseEntity<LaboratorioResponse> {
        val id = repository.findMaxId()?.plus(1)
        val laboratorios = repository.findByNumeroLaboratorio(numeroLaboratorio)
        var aberta = true
        laboratorios.forEach {
            if (it.numeroLaboratorio == numeroLaboratorio) aberta = false
        }
        if (aberta) {
            val laboratorio = Laboratorio(id = id, numeroLaboratorio = numeroLaboratorio, fechada = "N")
            val savedLaboratorio = repository.save(laboratorio)
            return ResponseEntity.status(CREATED).body(savedLaboratorio.toResponse())
        } else throw BadRequestException("Laboratório já está aberto")
    }

    @Operation(summary = "Verifica todos os laboratórios")
    @GetMapping()
    fun findAll(): List<Laboratorio> = repository.findAll()

    @Operation(summary = "Verifica laboratório por número")
    @GetMapping("/{numeroLaboratorio}")
    fun findByNumeroLaboratorio(@PathVariable numeroLaboratorio: Int): Array<Laboratorio> = repository.findByNumeroLaboratorio(numeroLaboratorio)

    @Operation(summary = "Deleta o laboratório")
    @DeleteMapping()
    fun deleteLaboratorio(laboratorio: Laboratorio) = repository.delete(laboratorio)

    @Operation(summary = "Verifica os pedidos deste laboratório")
    @GetMapping("/pedidos/{numeroLaboratorio}")
    fun findPedidosPorLaboratorio(@PathVariable("numeroLaboratorio") numeroLaboratorio: Int) = repository.findPedidosPorLaboratorio(numeroLaboratorio)
}
