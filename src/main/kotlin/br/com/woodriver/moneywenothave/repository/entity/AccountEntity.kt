package br.com.woodriver.moneywenothave.repository.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class AccountEntity(
        @Id
        val accountId: Int = Int.MIN_VALUE,
        val creditLimit: Int = Int.MIN_VALUE,
        val balance: Int = Int.MIN_VALUE,
)
