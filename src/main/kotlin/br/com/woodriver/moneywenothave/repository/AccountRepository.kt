package br.com.woodriver.moneywenothave.repository

import br.com.woodriver.moneywenothave.repository.entity.AccountEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository: JpaRepository<AccountEntity, Int>
