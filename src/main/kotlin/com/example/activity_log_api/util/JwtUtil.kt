package com.example.activity_log_api.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey
import java.util.Base64

@Component
class JwtUtil(
    @Value("\${jwt.secret}") secret: String
) {
    private val expiration: Long = 1000 * 60 * 60 // 1 hour
    private val secretKey: SecretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret))

    // Generate token with just username
    fun generateToken(username: String): String {
        val now = Date()
        val expiry = Date(now.time + expiration)

        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiry)
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
    }

    // Extract username from token
    fun extractUsername(token: String): String =
        extractAllClaims(token).subject

    // Check if token is valid (only expiration)
    fun isTokenValid(token: String): Boolean {
        val claims = extractAllClaims(token)
        return claims.expiration.after(Date())
    }

    private fun extractAllClaims(token: String): Claims =
        Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
}
