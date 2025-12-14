package com.hardi.noteservice.domain.dto

import java.time.Instant

data class NoteResponseDTO(
    val id: String,
    val title: String,
    val content: String,
    val color: String,
    val createdAt: Instant
)
