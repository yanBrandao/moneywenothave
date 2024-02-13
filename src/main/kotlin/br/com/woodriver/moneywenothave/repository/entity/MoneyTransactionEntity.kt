package br.com.woodriver.moneywenothave.repository.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.validation.constraints.NotEmpty
import org.apache.logging.log4j.util.Strings
import java.time.LocalDateTime

@Entity
data class MoneyTransactionEntity(
        @Id
        val id: String = Strings.EMPTY,
        val description: String = Strings.EMPTY,
        val amount: Int = Int.MIN_VALUE,
        val type: String = Strings.EMPTY,
        val date: String = LocalDateTime.now().toString(),
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "accountId")
        val account: AccountEntity = AccountEntity()
)
