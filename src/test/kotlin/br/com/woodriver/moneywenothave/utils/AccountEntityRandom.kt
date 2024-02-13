package br.com.woodriver.moneywenothave.utils

import br.com.woodriver.moneywenothave.domain.Account
import br.com.woodriver.moneywenothave.domain.MoneyTransaction
import br.com.woodriver.moneywenothave.repository.entity.AccountEntity
import org.jeasy.random.EasyRandom
import java.util.*


var easyRandom = EasyRandom()
inline fun <reified T> dummyObject(): T = easyRandom.nextObject(T::class.java)

/**
 * A AccountEntity with positive balance
 *
 * @property id AccountId fixed to 1
 * @property limit Credit limit fixed to 1000
 * @property balance Current Balance is 100
 */
fun dummySinglePositiveAccount(): AccountEntity =
    dummyAccountEntityObject(1, 1000, 100)

fun dummyAccountEntityObject(id: Int, limit: Int, balance: Int): AccountEntity =
        AccountEntity(
            accountId = id,
            balance = balance,
            creditLimit = limit
        )

