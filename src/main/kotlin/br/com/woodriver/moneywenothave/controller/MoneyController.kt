package br.com.woodriver.moneywenothave.controller

import br.com.woodriver.moneywenothave.controller.request.TransactionRequest
import br.com.woodriver.moneywenothave.controller.response.MoneyTransactionResponse
import br.com.woodriver.moneywenothave.controller.response.TimelineResponse
import br.com.woodriver.moneywenothave.domain.Account
import br.com.woodriver.moneywenothave.mapper.toDomain
import br.com.woodriver.moneywenothave.mapper.toResponse
import br.com.woodriver.moneywenothave.service.TransactionService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PathVariable

@RestController
@RequestMapping(value = ["/clientes"])
@SecurityRequirement(name = "basicAuth")
class MoneyController(
        val transactionService: TransactionService
) {

    @PostMapping(value = ["/{id}/transacoes"])
    fun transaction(
        @RequestBody body: TransactionRequest,
        @PathVariable id: String
    ): ResponseEntity<MoneyTransactionResponse>{
        logger.info("Received transaction $body with id $id")
        val account = transactionService.makeTransaction(body.toDomain(id.toInt()))
        logger.info("Transaction successfully completed with id $id")
        return ResponseEntity.ok(MoneyTransactionResponse(
                limit = account.limit,
                balance = account.balance
        ))
    }

    @GetMapping(value = ["/{id}/extrato"])
    fun transactions(@PathVariable id: String): ResponseEntity<TimelineResponse> {
        logger.info("Received request of id $id timeline")
        val timeline = transactionService.getTransactions(Account(id = id.toInt()))
        logger.info("Timeline retrieved successfully for id $id")
        return ResponseEntity.ok(timeline.toResponse())
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(MoneyController::class.java)
    }
}
