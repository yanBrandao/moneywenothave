package br.com.woodriver.moneywenothave.mapper

import br.com.woodriver.moneywenothave.controller.request.TransactionRequest
import br.com.woodriver.moneywenothave.controller.response.TimelineResponse
import br.com.woodriver.moneywenothave.domain.Account
import br.com.woodriver.moneywenothave.domain.MoneyTransaction
import br.com.woodriver.moneywenothave.domain.Timeline
import br.com.woodriver.moneywenothave.exception.BusinessException
import br.com.woodriver.moneywenothave.repository.entity.MoneyTransactionEntity
import java.util.*
import kotlin.collections.ArrayList

fun TransactionRequest.toDomain(accountId: Int): MoneyTransaction =
        MoneyTransaction(
                transactionId = UUID.randomUUID().toString(),
                type = when(type){
                    "c" -> MoneyTransaction.TransactionType.Credit
                    "d" -> MoneyTransaction.TransactionType.Debit
                    else -> throw BusinessException("Transaction type not accepted", 400, "MT002")
                },
                description = description,
                account = Account(
                        id = accountId
                ),
                amount = value
        )

fun ArrayList<MoneyTransactionEntity>.toDomain(): List<MoneyTransaction> =
        map {
            it.toDomain()
        }

fun MoneyTransactionEntity.toDomain(): MoneyTransaction =
        MoneyTransaction(
                transactionId = id,
                type = when(type) {
                    "Credit" -> MoneyTransaction.TransactionType.Credit
                    else -> MoneyTransaction.TransactionType.Debit
                },
                account = Account(account.accountId),
                amount = amount,
                description = description,
                date = date
        )

fun Timeline.toResponse(): TimelineResponse =
        TimelineResponse(
                timeline = TimelineResponse.TimelineDetailsResponse(
                        balance = account.balance,
                        limit = account.limit),
                transactions = transactions.map {
                    TimelineResponse.TransactionsResponse(
                            amount = it.amount,
                            description = it.description,
                            type = when(it.type) {
                                MoneyTransaction.TransactionType.Credit -> "c"
                                MoneyTransaction.TransactionType.Debit -> "d"
                            },
                            date = it.date
                            )
                        }
                )
