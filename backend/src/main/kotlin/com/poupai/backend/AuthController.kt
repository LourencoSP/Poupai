package com.poupai.backend

import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    data class RegisterRequest(val name: String, val email: String, val pass: String)
    data class LoginRequest(val email: String, val pass: String)

    @PostMapping("/register")
    fun register(@RequestBody req: RegisterRequest): ResponseEntity<Any> {
        if (userRepository.findByEmail(req.email).isPresent) {
            return ResponseEntity.badRequest().body("Email já cadastrado")
        }
        val user = User(
            name = req.name,
            email = req.email,
            password = passwordEncoder.encode(req.pass)
        )
        userRepository.save(user)
        return ResponseEntity.ok("Usuário criado!")
    }

    @PostMapping("/login")
    fun login(@RequestBody req: LoginRequest): ResponseEntity<Any> {
        val user = userRepository.findByEmail(req.email)
        if (user.isPresent && passwordEncoder.matches(req.pass, user.get().password)) {
            // Aqui retornamos um JSON simples com ID
            return ResponseEntity.ok(mapOf("message" to "Sucesso", "id" to user.get().id))
        }
        return ResponseEntity.status(401).body("Erro: Email ou senha incorretos")
    }
}