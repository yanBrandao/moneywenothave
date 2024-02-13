package br.com.woodriver.moneywenothave.utils

import br.com.woodriver.moneywenothave.repository.entity.MoneyTransactionEntity

fun dummyCreditTransaction(amount: Int): MoneyTransactionEntity =
    dummyMoneyTransactionObject("Credit", amount)

fun dummyDebitTransaction(amount: Int): MoneyTransactionEntity =
    dummyMoneyTransactionObject("Debit", amount)

fun dummyMoneyTransactionObject(type: String, amount: Int): MoneyTransactionEntity =
    MoneyTransactionEntity(
        id = dummyObject(),
        type = type,
        description = dummyObject(),
        amount = amount,
        account = dummySinglePositiveAccount(),
        date = dummyObject()
    )