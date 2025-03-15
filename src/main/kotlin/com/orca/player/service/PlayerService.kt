package com.orca.player.service

import com.orca.player.domain.Player
import com.orca.player.exception.BaseException
import com.orca.player.exception.ErrorCode
import org.springframework.stereotype.Service

@Service
class PlayerService(
    private val playerManager: PlayerManager,
    private val playerReader: PlayerReader
) {
    suspend fun generate(name: String, birth: String, loginId: String, password: String): Player {
        if (playerReader.byLoginId(loginId) != null) throw BaseException(ErrorCode.DUPLICATE_PLAYER)
        return playerManager.create(name, birth, loginId, password)
    }

    suspend fun get(id: String): Player {
        return playerReader.byId(id) ?: throw BaseException(ErrorCode.PLAYER_NOT_FOUND)
    }

    suspend fun verify(loginId: String): Player {
        return playerReader.byLoginId(loginId) ?: throw BaseException(ErrorCode.PLAYER_NOT_FOUND)
    }
}