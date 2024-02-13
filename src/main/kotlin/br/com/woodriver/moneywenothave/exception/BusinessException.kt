package br.com.woodriver.moneywenothave.exception

data class BusinessException(override val message: String, val statusCode: Int, val businessCode: String): RuntimeException(message)
