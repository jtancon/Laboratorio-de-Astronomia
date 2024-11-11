package br.pucpr.authserver.codgruest

import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/codgruest")
class CodGruEstController(val service: CodGruEstService, val repository: CodGruEstRepository) {

    @Operation(summary = "Pega os CodGruEst")
    @GetMapping()
    fun findAll(): List<CodGruEst> = service.getAllCodGruEst()

    @Operation(summary = "Salva um ou mais CodGruEst")
    @PostMapping()
    fun saveCodGruEst(@RequestBody codGruEstList: List<CodGruEst>): List<CodGruEst> {
        return codGruEstList.map { repository.save(it) }
    }

    @Operation(summary = "Pega o CodGruEst atravéz do ID")
    @GetMapping("/{id}")
    fun getCodGruEstById(@PathVariable id: Int): CodGruEst? = service.getCodGruEstByCodigo(id)
}