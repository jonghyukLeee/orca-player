package com.orca.player.external.kafka

import com.orca.player.service.PlayerService
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class PlayerEventConsumer(
    private val playerService: PlayerService
) {
    @KafkaListener(topics = ["join-accept"])
    suspend fun joinAccept(message: JoinAcceptMessage) {
        playerService.addClub(
            playerId = message.playerId,
            clubId = message.clubId
        )
    }

    @KafkaListener(topics = ["join-accept-failed"])
    suspend fun joinAcceptFailed(message: JoinAcceptMessage) {
        playerService.deleteClub(
            playerId = message.playerId,
            clubId = message.clubId
        )
    }
}