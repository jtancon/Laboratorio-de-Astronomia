package br.pucpr.authserver.codgruest

import org.springframework.data.jpa.repository.JpaRepository


interface CodGruEstRepository: JpaRepository<CodGruEst, Long> {

    fun save(codGruEst: CodGruEst): CodGruEst

    fun findCodGruEstByCodigo(id: Int): CodGruEst?

}