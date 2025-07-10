package br.pucpr.authserver.codgruest

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name = "tbl_CodGruEst")
class CodGruEst (
    @Id @GeneratedValue
    val codigo: Int?,
    @Column
    val descricao: String?

)