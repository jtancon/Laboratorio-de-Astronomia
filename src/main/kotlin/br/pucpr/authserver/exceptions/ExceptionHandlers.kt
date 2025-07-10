package br.pucpr.authserver.exceptions

import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class ExceptionHandlers {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException) =
        ex.bindingResult.allErrors
            .joinToString ( "\n" ) {
                "'${(it as FieldError).field}' ${it.defaultMessage}"
            }
            .let { ResponseEntity.badRequest().body(it) }


}