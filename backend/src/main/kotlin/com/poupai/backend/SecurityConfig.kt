package com.poupai.backend

import jakarta.persistence.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import java.util.Optional

// --- ENTIDADE (TABELA) ---
@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(unique = true, nullable = false)
    val email: String = "",
    @Column(nullable = false)
    val password: String = "",
    val name: String = ""
)

// --- REPOSITÓRIO ---
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): Optional<User>
}

// --- SEGURANÇA ---
@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers("/api/auth/**").permitAll() // Libera login
                it.anyRequest().authenticated()
            }
        return http.build()
    }
}