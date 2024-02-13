package br.com.woodriver.moneywenothave.domain

import br.com.woodriver.moneywenothave.exception.BusinessException
import br.com.woodriver.moneywenothave.mapper.toDomain
import br.com.woodriver.moneywenothave.repository.AccountRepository
import br.com.woodriver.moneywenothave.repository.MoneyTransactionRepository
import br.com.woodriver.moneywenothave.repository.entity.AccountEntity
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import kotlin.math.absoluteValue

data class Account(
        var id: Int,
        var limit: Int = Int.MIN_VALUE,
        var balance: Int = Int.MIN_VALUE
){
    fun loadAccount(accountRepository: AccountRepository) {
        val haveAccount = accountRepository.findById(id)

        when(haveAccount.isPresent) {
            true -> apply {
                limit = haveAccount.get().creditLimit
                balance = haveAccount.get().balance
            }
            else -> throw BusinessException(
                ACCOUNT_NOT_FOUND_EXCEPTION_MESSAGE,
                HttpStatus.NOT_FOUND.value(),
                ACCOUNT_NOT_FOUND_EXCEPTION_CODE)
        }
    }

    fun executeTransaction(moneyTransaction: MoneyTransaction, accountRepository: AccountRepository) {
        when(moneyTransaction.type) {
            MoneyTransaction.TransactionType.Credit -> makeCredit(moneyTransaction, accountRepository)
            MoneyTransaction.TransactionType.Debit -> makeDebit(moneyTransaction, accountRepository)
        }
    }

    private fun makeCredit(moneyTransaction: MoneyTransaction, accountRepository: AccountRepository) {
        balance = balance.plus(moneyTransaction.amount)

        accountRepository.save(toEntity())
    }

    private fun makeDebit(moneyTransaction: MoneyTransaction, accountRepository: AccountRepository) {
        if (balance.minus(moneyTransaction.amount) < (limit*-1)) {
            throw BusinessException(
                    message = TRANSACTION_EXCEPTION_MESSAGE,
                    statusCode = HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    businessCode = TRANSACTION_EXCEPTION_CODE
                    )
        } else {
            balance = balance.minus(moneyTransaction.amount)
        }

        accountRepository.save(toEntity())
    }

    fun toEntity(): AccountEntity =
            AccountEntity(
                    accountId = id,
                    balance = balance,
                    creditLimit = limit
            )

    fun getAllTransactions(moneyTransactionRepository: MoneyTransactionRepository): List<MoneyTransaction> {
        val transactions = moneyTransactionRepository.findFirst10ByAccountAccountIdOrderByDateDesc(id).toDomain()
        return transactions
    }

    companion object{
        const val TRANSACTION_EXCEPTION_MESSAGE = "Transaction exceed the credit limit"
        const val TRANSACTION_EXCEPTION_CODE = "MT001"
        const val ACCOUNT_NOT_FOUND_EXCEPTION_MESSAGE = "Account id not found"
        const val ACCOUNT_NOT_FOUND_EXCEPTION_CODE = "ACC001"
    }
}
