package com.orca.player.service

import com.orca.player.api.JoinApplicationStatus
import com.orca.player.domain.Player
import com.orca.player.exception.BaseException
import com.orca.player.exception.ErrorCode
import com.orca.player.external.club.ClubService
import com.orca.player.external.club.JoinApplicationResponse
import com.orca.player.external.kafka.EventPublisher
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service

@Service
class PlayerService(
    private val playerManager: PlayerManager,
    private val playerReader: PlayerReader,
    private val clubService: ClubService,
    private val eventPublisher: EventPublisher
) {
    suspend fun generate(name: String, birth: String, loginId: String, password: String): Player {
        if (playerReader.byLoginId(loginId) != null) throw BaseException(ErrorCode.DUPLICATE_PLAYER)
        return playerManager.create(name, birth, loginId, password)
    }

    suspend fun update(playerId: String, name: String): Player {
        return coroutineScope {
            val updatedPlayer = async { playerManager.update(playerId, name) }.await()
            launch { eventPublisher.playerUpdate(updatedPlayer) }
            updatedPlayer
        }
    }

    suspend fun getPlayer(playerId: String): Player {
        return playerReader.byId(playerId) ?: throw BaseException(ErrorCode.PLAYER_NOT_FOUND)
    }

    suspend fun verify(loginId: String): Player {
        return playerReader.byLoginId(loginId) ?: throw BaseException(ErrorCode.PLAYER_NOT_FOUND)
    }

    suspend fun getJoinApplications(playerId: String, status: JoinApplicationStatus): List<JoinApplicationResponse> {
        return clubService.getPlayerApplications(playerId, status)
    }
}