package br.com.woodriver.moneywenothave.service

import br.com.woodriver.moneywenothave.exception.BusinessException
import br.com.woodriver.moneywenothave.mapper.toDomain
import br.com.woodriver.moneywenothave.repository.AccountRepository
import br.com.woodriver.moneywenothave.repository.MoneyTransactionRepository
import br.com.woodriver.moneywenothave.utils.dummyMoneyTransactionObject
import br.com.woodriver.moneywenothave.utils.dummySinglePositiveAccount
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.should
import io.kotest.matchers.string.startWith
import io.mockk.*
import java.util.*

class TransactionServiceTest : FunSpec({

    val accountRepository: AccountRepository = mockk()
    val transactionRepository: MoneyTransactionRepository = mockk()
    val transactionService = TransactionService(accountRepository, transactionRepository)


    context("transactions tests") {
        withData(CREDIT, DEBIT) { type ->
            withData(10, 1000) { amount ->
                every { accountRepository.findById(any<Int>()) } returns Optional.of(
                    dummySinglePositiveAccount()
                )
                every { accountRepository.save(any()) } returns dummySinglePositiveAccount()
                every { transactionRepository.save(any()) } returns dummyMoneyTransactionObject(type, amount)
                transactionService.makeTransaction(
                    dummyMoneyTransactionObject(type, amount).toDomain()
                )
                verify { accountRepository.findById(any<Int>()) }
                verify { accountRepository.save(any()) }
                verify { transactionRepository.save(any()) }
                confirmVerified(accountRepository)
                confirmVerified(transactionRepository)
            }
        }

        withData(1101, Int.MAX_VALUE) { amount ->
            every { accountRepository.findById(any<Int>()) } returns Optional.of(
                dummySinglePositiveAccount()
            )
            every { accountRepository.save(any()) } returns dummySinglePositiveAccount()
            every { transactionRepository.save(any()) } returns dummyMoneyTransactionObject(DEBIT, amount)
            val exception = shouldThrow<BusinessException> {
                transactionService.makeTransaction(
                    dummyMoneyTransactionObject(DEBIT, amount).toDomain()
                )
            }
            exception.message should startWith("Transaction exceed the credit limit")
        }
    }

    afterTest {
        unmockkAll()
    }
}) {
    companion object {
        const val CREDIT = "Credit"
        const val DEBIT = "Debit"
    }
}
