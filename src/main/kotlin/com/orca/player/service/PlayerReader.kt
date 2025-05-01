package com.orca.player.service

import com.orca.player.domain.Player
import com.orca.player.repository.PlayerRepository
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.bson.types.ObjectId
import org.springframework.stereotype.Component

@Component
class PlayerReader(
    private val repository: PlayerRepository
) {
    suspend fun findById(id: ObjectId): Player? {
        return repository.findById(id).awaitSingleOrNull()
    }

    suspend fun findByLoginId(loginId: String): Player? {
        return repository.findByLoginId(loginId).awaitSingleOrNull()
    }
}