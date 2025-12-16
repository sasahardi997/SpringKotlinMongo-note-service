package com.hardi.noteservice.service

import com.hardi.noteservice.domain.Note
import com.hardi.noteservice.domain.dto.NoteRequestDTO
import com.hardi.noteservice.domain.dto.NoteResponseDTO
import com.hardi.noteservice.exception.BadRequestException
import com.hardi.noteservice.exception.ForbiddenException
import com.hardi.noteservice.exception.NoteNotFoundException
import com.hardi.noteservice.exception.UnauthorizedException
import com.hardi.noteservice.repository.NoteRepository
import org.bson.types.ObjectId
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class NoteService(val noteRepository: NoteRepository) {

    fun saveNote(noteRequestDTO: NoteRequestDTO): NoteResponseDTO {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: throw UnauthorizedException("User is not authenticated")

        val ownerId = authentication.principal as? String
            ?: throw ForbiddenException("Invalid authentication principal")


        val note = noteRequestDTO.let { it ->
            Note(
                id = it.id?.let { ObjectId(it) } ?: ObjectId.get(),
                title = it.title,
                content = it.content,
                color = it.color,
                createdAt = Instant.now(),
                ownerId = ObjectId(ownerId)
            )
        }

        val savedNote = noteRepository.save(note)

        return savedNote.toResponseDTO()
    }

    fun findByOwnerId(): List<NoteResponseDTO> {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: throw UnauthorizedException("User is not authenticated")

        val ownerId = authentication.principal as? String
            ?: throw ForbiddenException("Invalid authentication principal")

        val ownerObjectId = ObjectId(ownerId)
        return noteRepository.findByOwnerId(ownerObjectId)
            .map { it.toResponseDTO() }
    }

    fun deleteById(id: String) {
        val note = noteRepository.findById(ObjectId(id))
            .orElseThrow{ BadRequestException("Note not found with id $id") }

        val authentication = SecurityContextHolder.getContext().authentication
            ?: throw UnauthorizedException("User is not authenticated")

        val ownerId = authentication.principal as? String
            ?: throw ForbiddenException("Invalid authentication principal")

        if(note.ownerId.toHexString() == ownerId) {
            noteRepository.delete(note)
        }
    }


    private fun Note.toResponseDTO(): NoteResponseDTO {
        return NoteResponseDTO(
            id = id?.toHexString() ?: "",
            title = title,
            content = content,
            color = color,
            createdAt = createdAt
        )
    }
}