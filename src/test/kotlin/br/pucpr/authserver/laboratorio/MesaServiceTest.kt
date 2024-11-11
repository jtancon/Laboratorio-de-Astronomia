package br.pucpr.authserver.laboratorio

import io.mockk.mockk


internal class MesaControllerTest {

    private val mesaServiceRepositymockk = mockk<LaboratorioRepository>()
    private val controller = LaboratorioController(mesaServiceRepositymockk)

}