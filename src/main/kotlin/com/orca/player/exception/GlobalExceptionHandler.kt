package com.orca.player.exception

import com.orca.player.utils.baseResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.lang.Exception

@RestControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(Exception::class)
    fun undefinedException(e: Exception): ResponseEntity<ErrorResponse> {
        logger.error("Undefined Exception.\n$e")
        return baseResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR,
            body = ErrorResponse(BaseException(ErrorCode.UNDEFINED_EXCEPTION)))
    }

    @ExceptionHandler(BaseException::class)
    fun handleBaseException(e: BaseException): ResponseEntity<ErrorResponse> {
        return baseResponse(e.httpStatus, ErrorResponse(e))
    }
}