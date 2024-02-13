package br.com.woodriver.moneywenothave

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class ApiApplication

fun main(args: Array<String>) {
	runApplication<ApiApplication>(*args)
}
