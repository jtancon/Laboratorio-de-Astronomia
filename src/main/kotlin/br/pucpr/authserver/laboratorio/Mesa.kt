package br.pucpr.authserver.laboratorio

import jakarta.persistence.*


@Entity
@Table(name = "tblMesa")
open class Mesa(
    @Id @GeneratedValue
    var id: Long? = null,

    @Column(nullable = false)
    var numeroMesa: Int,

    @Column(nullable = false)
    var fechada: String?,

) {
    fun toResponse() = MesaResponse(numeroMesa, fechada)
}