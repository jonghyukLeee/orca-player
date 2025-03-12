package com.orca.player.service

import com.orca.player.domain.Player
import com.orca.player.repository.PlayerRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class PlayerReader(
    private val repository: PlayerRepository
) {
    suspend fun byId(id: String): Mono<Player> {
        return repository.findById(id)
    }

    suspend fun byDetails(name: String, birth: String): Mono<Player> {
        return repository.findByNameAndBirth(name, birth)
    }
}