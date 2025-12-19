package com.poupai.backend

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

data class DashboardResponse(
    val userName: String,
    val chartData: Map<String, Double>,
    val totalBalance: Double
)

@RestController
@RequestMapping("/api/dashboard")
class DashboardController(
    val userRepository: UserRepository,
    val expenseRepository: ExpenseRepository
) {

    @GetMapping("/{userId}")
    fun getDashboardData(@PathVariable userId: Long): ResponseEntity<Any> {
        // 1. Busca Segura com tratamento de erro 404
        val userOptional = userRepository.findById(userId)
        if (userOptional.isEmpty) {
            return ResponseEntity.status(404).body("Usuário não encontrado")
        }
        val user = userOptional.get()

        // 2. Pega os dados usando a interface segura (evita ClassCastException)
        val rawData = expenseRepository.findCategorySums(userId)

        // Converte a lista de interfaces para um Map
        val chartData = rawData.associate {
            it.getCategory() to it.getTotal()
        }

        // 3. Calcula o total (se estiver vazio, soma 0.0)
        val total = chartData.values.sum()

        return ResponseEntity.ok(
            DashboardResponse(user.name, chartData, total)
        )
    }

    @PostMapping("/add")
    fun addExpense(@RequestBody expense: Expense): ResponseEntity<Expense> {
        val saved = expenseRepository.save(expense)
        return ResponseEntity.ok(saved)
    }
}