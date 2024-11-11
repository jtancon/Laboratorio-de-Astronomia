package br.pucpr.authserver.laboratorio

import jakarta.persistence.*


@Entity
@Table(name = "tblLaboratorio")
open class Laboratorio(
    @Id @GeneratedValue
    var id: Long? = null,

    @Column(nullable = false)
    var numeroLaboratorio: Int,

    @Column(nullable = false)
    var fechada: String?,
) {
    fun toResponse() = LaboratorioResponse(numeroLaboratorio, fechada)
}
