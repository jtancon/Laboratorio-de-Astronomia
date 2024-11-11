package br.pucpr.authserver.laboratorio

import io.mockk.mockk


internal class LaboratorioControllerTest {

    private val mesaServiceRepositymockk = mockk<LaboratorioRepository>()
    private val controller = LaboratorioController(mesaServiceRepositymockk)

}