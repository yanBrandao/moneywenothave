package br.com.woodriver.moneywenothave.controller.response

import com.fasterxml.jackson.annotation.JsonProperty

data class MoneyTransactionResponse(
        @JsonProperty("limite")
        val limit: Int,
        @JsonProperty("saldo")
        val balance: Int
)
