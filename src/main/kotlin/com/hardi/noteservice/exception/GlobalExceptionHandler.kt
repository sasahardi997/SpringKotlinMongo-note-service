package com.hardi.noteservice.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<ApiErrorResponse> {
        val errors = ex.bindingResult.allErrors.map {
            it.defaultMessage ?: "Invalid value"
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ApiErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                errors[0],
                LocalDateTime.now()
            ))
    }

    @ExceptionHandler(NoteNotFoundException::class)
    fun handleNoteNotFoundException(ex: NoteNotFoundException): ResponseEntity<ApiErrorResponse> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ApiErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.message,
                LocalDateTime.now()
            ))
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException): ResponseEntity<ApiErrorResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ApiErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.message,
                LocalDateTime.now()
            ))
    }

    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnauthorizedException(ex: UnauthorizedException): ResponseEntity<ApiErrorResponse> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(ApiErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                ex.message,
                LocalDateTime.now()
            ))
    }

    @ExceptionHandler(ForbiddenException::class)
    fun handleForbiddenException(ex: ForbiddenException): ResponseEntity<ApiErrorResponse> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(ApiErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                ex.message,
                LocalDateTime.now()
            ))
    }
}