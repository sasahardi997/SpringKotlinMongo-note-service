package com.hardi.noteservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration
import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration

@SpringBootApplication(
    exclude = [UserDetailsServiceAutoConfiguration::class])
class NoteServiceApplication

fun main(args: Array<String>) {
	runApplication<NoteServiceApplication>(*args)
}
