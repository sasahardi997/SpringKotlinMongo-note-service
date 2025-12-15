package com.hardi.noteservice.controller

import com.hardi.noteservice.domain.dto.NoteRequestDTO
import com.hardi.noteservice.domain.dto.NoteResponseDTO
import com.hardi.noteservice.exception.ForbiddenException
import com.hardi.noteservice.exception.UnauthorizedException
import com.hardi.noteservice.service.NoteService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/notes")
class NoteController(val noteService: NoteService) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun saveNote(@Valid @RequestBody noteRequestDTO: NoteRequestDTO): NoteResponseDTO {
        return noteService.saveNote(noteRequestDTO)
    }

    @GetMapping
    fun findByOwnerId(): List<NoteResponseDTO> {
        return noteService.findByOwnerId()
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteById(@PathVariable("id") id: String) = noteService.deleteById(id)
}