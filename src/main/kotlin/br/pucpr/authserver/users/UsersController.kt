package br.pucpr.authserver.users

import br.pucpr.authserver.exceptions.BadRequestException
import br.pucpr.authserver.users.requests.LoginRequest
import br.pucpr.authserver.users.requests.UserRequest
import br.pucpr.authserver.users.responses.LoginResponse
import br.pucpr.authserver.users.responses.UserResponse
import io.swagger.v3.oas.annotations.Operation
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")

class UsersController(private val service: UsersService) {

    @Operation(summary = "Lista todos os Usuários")
    @GetMapping()
    fun listUsers(@RequestParam("role") role: String?): List<UserResponse>
    = service.findAll(role).map { it.toResponse() }

    @Operation(summary = "Cria um novo Usuário")
    @Transactional
    @PostMapping()
    fun createUser(@RequestBody @Validated req: UserRequest) : ResponseEntity<UserResponse> {
        val user = User(email = req.email, password = req.password, role = req.role?: "USER" , name = req.name?: req.email)
        val saved = service.save(user).toResponse()
        return ResponseEntity.status(HttpStatus.CREATED).body(saved)
    }

    @Operation(summary = "Pega o Usuário atravéz do ID")
    @GetMapping("/{id}")
    fun getUser(@PathVariable("id") id: Long): ResponseEntity<UserResponse> =
        service.getById(id)
            .map { ResponseEntity.ok(it.toResponse()) }
            .orElse(ResponseEntity.notFound().build())

    @Operation(summary = "Deleta um Usuário pelo ID")
    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Long): BadRequestException {
        return if (service.getById(id).isPresent) {
            val user = service.getById(id).get()
            return if (service.delete(id)) {
                BadRequestException("Delete work")
            } else {
                BadRequestException("User Not Found")
            }
        } else {
            BadRequestException("User not found")
        }
    }

    @PostMapping("/login")
    fun login(@Validated @RequestBody credentials: LoginRequest): ResponseEntity<LoginResponse> =
            service.login(credentials)
                    ?.let { ResponseEntity.ok(it) }
                    ?: ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()


    private fun User.toResponse() = UserResponse(id!!, email, name, role)

    companion object val log = LoggerFactory.getLogger(UsersController::class.java)

}