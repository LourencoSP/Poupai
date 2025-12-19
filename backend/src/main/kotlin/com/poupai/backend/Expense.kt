package com.poupai.backend

import jakarta.persistence.*

@Entity
@Table(name = "expenses")
data class Expense(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val userId: Long = 0, // Liga a despesa ao usuário
    val category: String = "",
    val amount: Double = 0.0
)