package br.com.woodriver.moneywenothave.service

import br.com.woodriver.moneywenothave.domain.Account
import br.com.woodriver.moneywenothave.domain.MoneyTransaction
import br.com.woodriver.moneywenothave.domain.Timeline
import br.com.woodriver.moneywenothave.repository.AccountRepository
import br.com.woodriver.moneywenothave.repository.MoneyTransactionRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TransactionService(
        val accountRepository: AccountRepository,
        val moneyTransactionRepository: MoneyTransactionRepository
) {

    fun makeTransaction(transaction: MoneyTransaction): Account{
        transaction.apply {
            logger.info("Starting to load account from database with id ${transaction.account.id}")
            account.apply {
                loadAccount(accountRepository)
            }
            logger.info("Account ${transaction.account.id} load successfully with " +
                    "limit=${transaction.account.limit} " +
                    "and balance=${transaction.account.balance}")
            
            logger.info("Starting to make a ${transaction.type} transaction with balance " +
                    "${transaction.account.balance}")
            makeTransaction(moneyTransactionRepository, accountRepository)
            logger.info("Done to make a ${transaction.type} transaction successfully! Now account " +
                    "${transaction.account.id} have ${transaction.account.balance}")
        }
        return transaction.account
    }

    fun getTransactions(account: Account): Timeline {
        account.apply {
            loadAccount(accountRepository)
        }

        return Timeline(
                account,
                account.getAllTransactions(moneyTransactionRepository)
        )
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(TransactionService::class.java)
    }
}
