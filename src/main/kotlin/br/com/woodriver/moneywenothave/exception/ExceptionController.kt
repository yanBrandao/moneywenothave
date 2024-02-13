package br.com.woodriver.moneywenothave.exception

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.function.Consumer


@RestControllerAdvice
class ExceptionController {

    @ExceptionHandler(BusinessException::class)
    fun businessHandler(ex: BusinessException): ResponseEntity<Any>{
        logger.error("Business Exception is thrown: ${ex.message}")
        return ResponseEntity(ErrorResponse(
                code = ex.businessCode,
                message = "Erro de negocio: ${ex.message}."
        ), HttpStatusCode.valueOf(ex.statusCode))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(
            ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        logger.error("Method Argument Not valid Exception is thrown: ${ex.message}")
        val errors: MutableMap<String, String?> = HashMap()
        ex.bindingResult.allErrors.forEach(Consumer { error: ObjectError ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.getDefaultMessage()
            errors[fieldName] = errorMessage
        })
        return ResponseEntity(ErrorResponse(
                code = INPUT_INVALID_ARGUMENT_EXCEPTION_CODE,
                message = "Erro de entrada: $errors"
        ), HttpStatusCode.valueOf(400))
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleNotReadableExceptions(
            ex: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        logger.error("Not Readable Exception is thrown: ${ex.message}")
        return ResponseEntity(ErrorResponse(
                code = INPUT_NOT_READABLE_EXCEPTION_CODE,
                message = "Erro de entrada: ${ex.message}"
        ), HttpStatusCode.valueOf(400))
    }

    companion object{
        val logger: Logger = LoggerFactory.getLogger(ExceptionController::class.java)
        const val INPUT_INVALID_ARGUMENT_EXCEPTION_CODE = "IN001"
        const val INPUT_NOT_READABLE_EXCEPTION_CODE = "IN002"
    }
}
