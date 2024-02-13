package br.com.woodriver.moneywenothave.repository

import br.com.woodriver.moneywenothave.domain.MoneyTransaction
import br.com.woodriver.moneywenothave.repository.entity.MoneyTransactionEntity
import org.springframework.data.jpa.repository.JpaRepository


interface MoneyTransactionRepository: JpaRepository<MoneyTransactionEntity, String> {

    fun findFirst10ByAccountAccountIdOrderByDateDesc(accountId: Int): ArrayList<MoneyTransactionEntity>
}
