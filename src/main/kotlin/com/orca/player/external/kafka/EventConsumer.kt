package com.orca.player.external.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.orca.player.external.redis.RedisService
import com.orca.player.service.PlayerManager
import org.bson.types.ObjectId
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class EventConsumer(
    private val playerManager: PlayerManager,
    private val redisService: RedisService,
    private val eventPublisher: EventPublisher,
    private val objectMapper: ObjectMapper
) {
    @KafkaListener(topics = [EventTopics.JOIN_ACCEPTED])
    suspend fun joinAccepted(txId: String) {
        val jsonString = redisService.get(txId)
        val message = parseToMessage<JoinAcceptMessage>(jsonString)

        try {
            playerManager.addClub(
                playerId = ObjectId(message.playerId),
                clubId = ObjectId(message.clubId)
            )
        } catch (e: Exception) {
            eventPublisher.send(EventTopics.JOIN_ACCEPT_FAILED, jsonString)
        }
    }

    @KafkaListener(topics = [EventTopics.JOIN_ACCEPT_FAILED])
    suspend fun joinAcceptFailed(txId: String) {
        val jsonString = redisService.get(txId)
        val message = parseToMessage<JoinAcceptMessage>(jsonString)

        playerManager.deleteClub(
            playerId = ObjectId(message.playerId),
            clubId = ObjectId(message.clubId)
        )
    }

    @KafkaListener(topics = [EventTopics.CLUB_CREATED])
    suspend fun clubCreated(txId: String) {
        try {
            val jsonString = redisService.get(txId)
            val message = parseToMessage<ClubCreatedMessage>(jsonString)

            playerManager.addClub(
                playerId = ObjectId(message.playerId),
                clubId = ObjectId(message.clubId)
            )
        } catch (e: Exception) {
            eventPublisher.send(EventTopics.CLUB_CREATE_FAILED, txId)
        }
    }

    private suspend inline fun <reified T> parseToMessage(jsonString: String): T {
        return objectMapper.readValue<T>(jsonString)
    }
}