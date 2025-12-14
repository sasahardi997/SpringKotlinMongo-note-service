package com.hardi.noteservice.domain.dto

import jakarta.validation.constraints.NotBlank
import org.bson.types.ObjectId

data class NoteRequestDTO(
    val id: String?,

    @field:NotBlank(message = "Title can't be blank")
    val title: String,

    val content: String,
    val color: String
)
