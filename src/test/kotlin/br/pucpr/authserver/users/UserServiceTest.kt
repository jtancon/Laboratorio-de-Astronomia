package br.pucpr.authserver.users

import br.pucpr.authserver.exceptions.BadRequestException
import br.pucpr.authserver.security.Jwt
import br.pucpr.authserver.Stubs.userStub
import br.pucpr.authserver.Stubs.admStub
import io.kotest.assertions.throwables.shouldThrow

import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage
import io.mockk.*
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull


internal class UserServiceTest {

    private val userRepositoryMockk = mockk<UsersRepository>()
    private val jwtMockk = mockk<Jwt>()

    private val service = UsersService(userRepositoryMockk, jwtMockk)

    @Test
    fun `should return false if User does not exist`(){
        every { userRepositoryMockk.findByIdOrNull(1) } returns null
        service.delete(1) shouldBe false
    }

    @Test
    fun `should delete must return true if user is deleted`(){
        val user = userStub()
        every { userRepositoryMockk.findByIdOrNull(1) } returns user
        justRun { userRepositoryMockk.delete(user) }
        service.delete(1) shouldBe true
    }

    @Test
    fun `delete should throw a BadRequestException if user is the only ADM`(){
        val adm = admStub()
        every { userRepositoryMockk.findByIdOrNull(1) } returns adm
        every { userRepositoryMockk.findallByRole("ADM") } returns listOf(adm)
        shouldThrow<BadRequestException> {
            service.delete(1)
        } shouldHaveMessage "Não é possível deletar o único usuário ADM"
    }

    @Test
    fun `delete admin if have more then 1 ADM`() {
        val adm = admStub()
        every { userRepositoryMockk.findByIdOrNull(1) } returns adm
        every { userRepositoryMockk.findallByRole("ADM") } returns listOf(adm, adm)
        justRun { userRepositoryMockk.delete(adm) }
        service.delete(1) shouldBe true

    }


}

