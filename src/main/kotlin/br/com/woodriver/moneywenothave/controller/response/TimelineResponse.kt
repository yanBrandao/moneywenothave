package br.com.woodriver.moneywenothave.controller.response

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class TimelineResponse(
        @JsonProperty(value = "saldo")
        val timeline: TimelineDetailsResponse,
        @JsonProperty("ultimas_transacoes")
        val transactions: List<TransactionsResponse>
) {
    data class TimelineDetailsResponse(
            @JsonProperty("total")
            val balance: Int,
            @JsonProperty("data_extrato")
            val date: String = LocalDateTime.now().toString(),
            @JsonProperty("limite")
            val limit: Int
    )

    data class TransactionsResponse(
            @JsonProperty("valor")
            val amount: Int,
            @JsonProperty("tipo")
            val type: String,
            @JsonProperty("descricao")
            val description: String,
            @JsonProperty("realizada_em")
            val date: String
    )
}
