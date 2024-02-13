package br.com.woodriver.moneywenothave.domain

data class Timeline(
        val account: Account,
        val transactions: List<MoneyTransaction>
)
