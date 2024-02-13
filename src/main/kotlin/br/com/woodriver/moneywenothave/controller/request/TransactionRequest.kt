package br.com.woodriver.moneywenothave.controller.request

import com.fasterxml.jackson.annotation.JsonProperty

data class TransactionRequest(
        @JsonProperty("valor")
        val value: Int,
        @JsonProperty("tipo")
        val type: String,
        @JsonProperty("descricao")
        val description: String
)
