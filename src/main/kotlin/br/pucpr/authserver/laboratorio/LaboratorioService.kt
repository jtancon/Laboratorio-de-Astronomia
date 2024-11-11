package br.pucpr.authserver.laboratorio

import org.springframework.stereotype.Service

@Service
class LaboratorioService(val repository: LaboratorioRepository) {

    fun saveLaboratorio(laboratorio: Laboratorio) = repository.save(laboratorio)

    fun getNumeroLaboratorio(numeroLaboratorio: Int) = repository.findByNumeroLaboratorio(numeroLaboratorio)

    fun getPedidosPorLaboratorio(numeroLaboratorio: Int) = repository.findPedidosPorLaboratorio(numeroLaboratorio)
}
