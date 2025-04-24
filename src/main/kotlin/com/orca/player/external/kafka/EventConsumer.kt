package com.orca.player.external.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.orca.player.external.redis.RedisService
import com.orca.player.service.PlayerManager
import org.bson.types.ObjectId
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class EventConsumer(
    private val playerManager: PlayerManager,
    private val redisService: RedisService,
    private val eventPublisher: EventPublisher
) {
    @KafkaListener(topics = [EventTopics.JOIN_ACCEPTED])
    suspend fun joinAccepted(message: JoinAcceptMessage) {
        playerManager.addClub(
            playerId = ObjectId(message.playerId),
            clubId = ObjectId(message.clubId)
        )
    }

    @KafkaListener(topics = [EventTopics.JOIN_ACCEPT_FAILED])
    suspend fun joinAcceptFailed(message: JoinAcceptMessage) {
        playerManager.deleteClub(
            playerId = ObjectId(message.playerId),
            clubId = ObjectId(message.clubId)
        )
    }

    @KafkaListener(topics = [EventTopics.CLUB_CREATED])
    suspend fun clubCreated(txId: String) {
        try {
            val jsonString = redisService.get(txId)
            val message = ObjectMapper().readValue(jsonString, ClubCreatedMessage::class.java)

            playerManager.addClub(
                playerId = ObjectId(message.playerId),
                clubId = ObjectId(message.clubId)
            )
        } catch (e: Exception) {
            eventPublisher.send(EventTopics.CLUB_CREATE_FAILED, txId)
        }
    }
}