package br.pucpr.authserver.exceptions

import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(NOT_FOUND)
class NotFoundException(
    message: String = NOT_FOUND.reasonPhrase,
    cause: Throwable? = null
): IllegalArgumentException(message, cause)