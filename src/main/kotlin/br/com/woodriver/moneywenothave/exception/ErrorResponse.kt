package br.com.woodriver.moneywenothave.exception

import com.fasterxml.jackson.annotation.JsonProperty

data class ErrorResponse(
        @JsonProperty("codigo")
        val code: String,
        @JsonProperty("mensagem:")
        val message: String
)
