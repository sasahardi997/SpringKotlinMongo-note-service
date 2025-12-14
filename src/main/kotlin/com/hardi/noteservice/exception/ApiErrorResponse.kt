package com.hardi.noteservice.exception

import java.time.LocalDateTime

data class ApiErrorResponse(
    val code: Int,
    val message: String?,
    val timestamp: LocalDateTime
)