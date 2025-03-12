package com.orca.player.service

import com.orca.player.domain.Player
import com.orca.player.repository.PlayerRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class PlayerManager(
    private val repository: PlayerRepository
) {
    suspend fun create(name: String, birth: String): Mono<Player> {
        return repository.save(
            Player(
                name = name
                , birth = birth
            )
        )
    }
}