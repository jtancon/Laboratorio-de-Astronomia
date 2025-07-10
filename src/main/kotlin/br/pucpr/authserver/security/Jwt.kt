package br.pucpr.authserver.security

import br.pucpr.authserver.users.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.jackson.io.JacksonDeserializer
import io.jsonwebtoken.jackson.io.JacksonSerializer
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.apache.tomcat.util.http.parser.Authorization
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.*

@Component
class Jwt {

    fun createToken(user: User) =
        UserToken(
            id = user.id ?: -1,
            name = user.name,
            role = setOf(user.role ?: "USER")
        ).let {
            Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(SECRET.toByteArray()))
                .serializeToJsonWith(JacksonSerializer())
                .setIssuedAt(utcNow().toDate())
                .setExpiration(utcNow().plusHours(EXPIRE_HOURS).toDate())
                .setIssuer(ISSUER)
                .setSubject(it.id.toString())
                .addClaims(mapOf(USER to it))
                .compact()
        }

    fun extract(req: HttpServletRequest): Authentication? {

        val header = req.getHeader(AUTHORIZATION)
        if (header == null || !header.startsWith(PREFIX)) return null
        val token = header.replace(PREFIX, "").trim()

        val claims = Jwts.parserBuilder()
            .setSigningKey(SECRET.toByteArray())
            .deserializeJsonWith(JacksonDeserializer(
                mapOf(USER to UserToken::class.java)
            )).build()
            .parseClaimsJws(token)
            .body

        if (claims.issuer != ISSUER) return null
        val user = claims.get(USER, UserToken::class.java)

        val authorities = user.role.map { SimpleGrantedAuthority("ROLE_$it") }
        return UsernamePasswordAuthenticationToken.authenticated(user, user.id, authorities)
    }

    companion object{
        private const val PREFIX = "Bearer"
        private const val ISSUER = "Minecraft"
        private const val SECRET = "uEX#(}:@Hz!SrRG*8p[f>m!0uY[P@7Kc"
        private const val USER = "user"
        private const val EXPIRE_HOURS = 24L

        private fun ZonedDateTime.toDate(): Date = Date.from(this.toInstant())
        private fun utcNow(): ZonedDateTime = ZonedDateTime.now(ZoneOffset.UTC)
    }

}