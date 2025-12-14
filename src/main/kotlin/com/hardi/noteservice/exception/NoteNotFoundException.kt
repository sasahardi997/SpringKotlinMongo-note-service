package com.hardi.noteservice.exception

import java.lang.RuntimeException

class NoteNotFoundException(message: String): RuntimeException(message) {
}