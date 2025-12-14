package com.hardi.noteservice.repository

import com.hardi.noteservice.domain.Note
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface NoteRepository: MongoRepository<Note, ObjectId> {

    fun findByOwnerId(owner: ObjectId): List<Note>
}