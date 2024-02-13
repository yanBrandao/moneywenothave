package br.com.woodriver.moneywenothave.domain

import br.com.woodriver.moneywenothave.repository.AccountRepository
import br.com.woodriver.moneywenothave.repository.MoneyTransactionRepository
import br.com.woodriver.moneywenothave.repository.entity.AccountEntity
import br.com.woodriver.moneywenothave.repository.entity.MoneyTransactionEntity
import java.time.LocalDateTime

data class MoneyTransaction(
        val transactionId: String,
        val type: TransactionType,
        val description: String,
        val amount: Int,
        var account: Account,
        var date: String = LocalDateTime.now().toString(),
) {
    enum class TransactionType {
        Credit, Debit
    }

    fun makeTransaction(moneyTransactionRepository: MoneyTransactionRepository, accountRepository: AccountRepository) {
        account.executeTransaction(this, accountRepository)
        moneyTransactionRepository.save(toEntity())
    }

    private fun toEntity(): MoneyTransactionEntity =
            MoneyTransactionEntity(
                    id = transactionId,
                    description = description,
                    amount = amount,
                    type = type.toString(),
                    account = AccountEntity(accountId = account.id)
            )

}
