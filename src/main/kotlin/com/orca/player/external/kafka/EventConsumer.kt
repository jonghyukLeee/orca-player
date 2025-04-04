package com.orca.player.external.kafka

import com.orca.player.service.PlayerManager
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class EventConsumer(
    private val playerManager: PlayerManager
) {
    @KafkaListener(topics = ["join-accept"])
    suspend fun joinAccept(message: JoinAcceptMessage) {
        playerManager.addClub(
            playerId = message.playerId,
            clubId = message.clubId
        )
    }

    @KafkaListener(topics = ["join-accept-failed"])
    suspend fun joinAcceptFailed(message: JoinAcceptMessage) {
        playerManager.deleteClub(
            playerId = message.playerId,
            clubId = message.clubId
        )
    }
}