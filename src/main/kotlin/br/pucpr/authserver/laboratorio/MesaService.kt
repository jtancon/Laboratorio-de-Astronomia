package br.pucpr.authserver.laboratorio

import org.springframework.stereotype.Service

@Service
class MesaService(val repository: MesaRepository) {

    fun saveMesa(mesa: Mesa) = repository.save(mesa)

    fun getNumeroMesa(numeroMesa: Int) = repository.findByNumeroMesa(numeroMesa)

    fun gtePedidosPorMesa(numeroMesa: Int) = repository.findPedidosPorMesa(numeroMesa)
}