package com.poupai.backend

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

// Interface para projeção de dados (Tipo seguro em vez de Array<Any>)
interface CategorySum {
    fun getCategory(): String
    fun getTotal(): Double
}

interface ExpenseRepository : JpaRepository<Expense, Long> {
    fun findByUserId(userId: Long): List<Expense>

    // Consulta corrigida para usar a interface CategorySum
    @Query("SELECT e.category as category, SUM(e.amount) as total FROM Expense e WHERE e.userId = :userId GROUP BY e.category")
    fun findCategorySums(userId: Long): List<CategorySum>
}