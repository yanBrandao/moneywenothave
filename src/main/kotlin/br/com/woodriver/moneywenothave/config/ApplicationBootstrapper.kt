package br.com.woodriver.moneywenothave.config

import br.com.woodriver.moneywenothave.domain.Account
import br.com.woodriver.moneywenothave.repository.AccountRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationBootstrapper(
        val accountRepository: AccountRepository
): ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        accountRepository.save(
                Account(
                        id = 1,
                        limit = 100000,
                        balance = 0
                ).toEntity()
        )

        accountRepository.save(
                Account(
                        id = 2,
                        limit = 80000,
                        balance = 0
                ).toEntity()
        )

        accountRepository.save(
                Account(
                        id = 3,
                        limit = 1000000,
                        balance = 0
                ).toEntity()
        )

        accountRepository.save(
                Account(
                        id = 4,
                        limit = 10000000,
                        balance = 0
                ).toEntity()
        )

        accountRepository.save(
                Account(
                        id = 5,
                        limit = 500000,
                        balance = 0
                ).toEntity()
        )
    }
}
