package com.hardi.noteservice.exception

import java.lang.RuntimeException

class UnauthorizedException(message: String): RuntimeException(message) {
}