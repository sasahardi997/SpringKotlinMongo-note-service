package com.hardi.noteservice.service

import com.hardi.noteservice.domain.Note
import com.hardi.noteservice.domain.dto.NoteRequestDTO
import com.hardi.noteservice.domain.dto.NoteResponseDTO
import com.hardi.noteservice.exception.NoteNotFoundException
import com.hardi.noteservice.repository.NoteRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class NoteService(val noteRepository: NoteRepository) {

    fun saveNote(noteRequestDTO: NoteRequestDTO): NoteResponseDTO {
        val note = noteRequestDTO.let { it ->
            Note(
                id = it.id?.let { ObjectId(it) } ?: ObjectId.get(),
                title = it.title,
                content = it.content,
                color = it.color,
                createdAt = Instant.now(),
                ownerId = ObjectId()
            )
        }

        val savedNote = noteRepository.save(note)

        return savedNote.toResponseDTO()
    }

    fun findByOwnerId(ownerId: String): List<NoteResponseDTO> {
        val ownerObjectId = ObjectId(ownerId)
        return noteRepository.findByOwnerId(ownerObjectId)
            .map { it.toResponseDTO() }
    }

    fun deleteById(id: String) {
        val objectId = ObjectId(id)
        val note = noteRepository.findById(objectId)
        if (note.isEmpty) throw NoteNotFoundException("Note with id $id not found")
        noteRepository.delete(note.get())
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