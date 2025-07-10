package br.pucpr.authserver.exceptions

import org.springframework.http.HttpStatus.I_AM_A_TEAPOT
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(I_AM_A_TEAPOT)
class NotaTeaPotException(
    message: String = I_AM_A_TEAPOT.reasonPhrase,
    cause: Throwable? = null
): IllegalArgumentException(message, cause)