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
import org.bson.types.ObjectId
import org.springframework.stereotype.Service

@Service
class PlayerService(
    private val playerManager: PlayerManager,
    private val playerReader: PlayerReader,
    private val clubService: ClubService,
    private val eventPublisher: EventPublisher
) {
    suspend fun generate(name: String, birth: String, loginId: String, password: String): Player {
        if (playerReader.findByLoginId(loginId) != null) throw BaseException(ErrorCode.DUPLICATE_PLAYER)
        return playerManager.create(name, birth, loginId, password)
    }

    suspend fun update(playerId: ObjectId, name: String): Player {
        return coroutineScope {
            val originPlayer = getPlayer(playerId)
            try {
                val updatedPlayer = async { playerManager.update(playerId, name) }.await()
                launch { eventPublisher.playerUpdated(updatedPlayer) }
                updatedPlayer
            } catch (e: Exception) {
                playerManager.update(playerId, originPlayer.name)
                eventPublisher.playerUpdateFailed(originPlayer)
                throw BaseException(ErrorCode.PLAYER_UPDATE_FAILED)
            }
        }
    }

    suspend fun getPlayer(playerId: ObjectId): Player {
        return playerReader.findById(playerId) ?: throw BaseException(ErrorCode.PLAYER_NOT_FOUND)
    }

    suspend fun verify(loginId: String): Player {
        return playerReader.findByLoginId(loginId) ?: throw BaseException(ErrorCode.PLAYER_NOT_FOUND)
    }

    suspend fun getJoinApplications(playerId: ObjectId, status: JoinApplicationStatus): List<JoinApplicationResponse> {
        return clubService.getPlayerApplications(playerId, status)
    }
}