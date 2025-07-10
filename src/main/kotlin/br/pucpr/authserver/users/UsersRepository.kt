package br.pucpr.authserver.users

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository

interface UsersRepository : JpaRepository<User, Long>{

    @Query(value = "SELECT DISTINCT u FROM User u WHERE u.role = :role ORDER BY u.name")
    fun findallByRole(role: String): List<User>

    fun findUserByEmail(email: String): User?

    fun deleteUserById(id: Long) : Long

}