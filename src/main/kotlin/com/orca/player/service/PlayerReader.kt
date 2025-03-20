package com.orca.player.service

import com.orca.player.domain.Player
import com.orca.player.repository.PlayerRepository
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Component

@Component
class PlayerReader(
    private val repository: PlayerRepository
) {
    suspend fun byId(id: String): Player? {
        return repository.findById(id).awaitSingleOrNull()
    }

    suspend fun byLoginId(loginId: String): Player? {
        return repository.findByLoginId(loginId).awaitSingleOrNull()
    }

    suspend fun byDetails(name: String, birth: String): Player? {
        return repository.findByNameAndBirth(name, birth).awaitSingleOrNull()
    }
}