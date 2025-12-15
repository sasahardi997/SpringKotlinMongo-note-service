package com.hardi.noteservice.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.Date

@Service
class JwtService(
    @Value($$"${jwt.secret}")
    private val jwtSecret: String
) {

    private enum class TokenType {
        ACCESS, REFRESH
    }

    private val secretKey = Keys.hmacShaKeyFor(jwtSecret.toByteArray())

    private val accessTokenValidityMs = 300_000L //5 minutes
    internal val refreshTokenValidityMS = 604_800_000L //7 days

    private fun generateToken(
        userId: String,
        type: TokenType,
        expiry: Long
    ) : String {
        val now = Date()
        val expiryDate = Date(now.time + expiry)
        return Jwts.builder()
            .subject(userId)
            .claim("type", type.name)
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(secretKey, Jwts.SIG.HS256)
            .compact()
    }

    fun generateAccessToken(userId: String): String = generateToken(
        userId,
        TokenType.ACCESS,
        accessTokenValidityMs)

    fun generateRefreshToken(userId: String): String = generateToken(
        userId,
        TokenType.REFRESH,
        refreshTokenValidityMS)

    fun getUserIdFromToken(token: String): String {
        val rawToken = if(token.startsWith("Bearer ")) {
            token.removePrefix("Bearer ")
        } else token

        val claims = parseAllClaims(rawToken) ?: throw IllegalArgumentException("Invalid token")
        return claims.subject
    }

    fun validateAccessToken(token: String): Boolean {
        val claims = parseAllClaims(token) ?: return false

        val tokenType = claims["type"] as? String ?: return false
        return tokenType == TokenType.ACCESS.name
    }

    fun validateRefreshToken(token: String): Boolean {
        val claims = parseAllClaims(token) ?: return false

        val tokenType = claims["type"] as? String ?: return false
        return tokenType == TokenType.REFRESH.name
    }



    private fun parseAllClaims(token: String): Claims? {
        return try {
            Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .payload
        } catch (_: Exception) {
            null
        }
    }
}