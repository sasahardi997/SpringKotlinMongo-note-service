package com.hardi.noteservice.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NoteNotFoundException::class)
    fun handleNoteNotFoundException(ex: NoteNotFoundException): ResponseEntity<ApiErrorResponse> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ApiErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.message,
                LocalDateTime.now()
            ))
    }
}