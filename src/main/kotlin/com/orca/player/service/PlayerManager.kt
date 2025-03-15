package com.orca.player.service

import com.orca.player.domain.Player
import com.orca.player.repository.PlayerRepository
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component

@Component
class PlayerManager(
    private val repository: PlayerRepository
) {
    suspend fun create(name: String, birth: String, loginId: String, password: String): Player {
        return repository.save(
            Player(
                name = name, birth = birth, loginId = loginId, password = password
            )
        ).awaitSingle()
    }
}