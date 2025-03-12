package com.orca.player.service

import com.orca.player.domain.Player
import com.orca.player.exception.BaseException
import com.orca.player.exception.ErrorCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
class PlayerService(
    private val playerManager: PlayerManager,
    private val playerReader: PlayerReader
) {
    suspend fun generate(name: String, birth: String): Player {
        return withContext(Dispatchers.IO) {
            playerReader.byDetails(name, birth).awaitSingleOrNull() ?: throw BaseException(ErrorCode.DUPLICATE_PLAYER)

            playerManager.create(name, birth).awaitSingle()
        }
    }

    suspend fun getById(id: String): Player {
        return withContext(Dispatchers.IO) {
            playerReader.byId(id).awaitSingleOrNull() ?: throw BaseException(ErrorCode.PLAYER_NOT_FOUND)
        }
    }
}