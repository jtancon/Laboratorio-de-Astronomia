package br.pucpr.authserver.codgruest

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service


@Service
class CodGruEstService(val repository: CodGruEstRepository) {

        fun saveCodGruEst(codGruEst: CodGruEst) {
            log.info("CodGruEst descrição={} salvo", codGruEst.descricao)
            repository.save(codGruEst)
        }

        fun codGruEstGetById(id: Long) = repository.findById(id)

        fun getAllCodGruEst() : MutableList<CodGruEst> = repository.findAll()

        fun getCodGruEstByCodigo(codigo: Int): CodGruEst? {
            return repository.findCodGruEstByCodigo(codigo)
        }

        fun deleteCodGruEst(id: Long) {
            log.info("CodGruEst id={} deletado", id)
            repository.deleteById(id)
        }


        companion object {
            val log = LoggerFactory.getLogger(CodGruEstService::class.java)
        }
}