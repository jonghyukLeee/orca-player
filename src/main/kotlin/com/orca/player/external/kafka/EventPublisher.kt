package com.orca.player.external.kafka

import com.orca.player.domain.Player
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import org.springframework.stereotype.Service

@Service
class EventPublisher(
    private val reactiveKafkaProducerTemplate: ReactiveKafkaProducerTemplate<String, Any>
) {
    suspend fun playerUpdated(updatedPlayer: Player) {
        send(EventTopics.PLAYER_UPDATED,
            PlayerMessage(
                id = updatedPlayer.id.toString(),
                name = updatedPlayer.name,
                birth = updatedPlayer.birth,
                loginId = updatedPlayer.loginId,
            )
        )
    }

    suspend fun playerUpdateFailed(originPlayer: Player) {
        send(EventTopics.PLAYER_UPDATE_FAILED,
            PlayerMessage(
                id = originPlayer.id.toString(),
                name = originPlayer.name,
                birth = originPlayer.birth,
                loginId = originPlayer.loginId,
            )
        )
    }

    suspend fun send(topic: String, message: Any) {
        reactiveKafkaProducerTemplate.send(topic, message).awaitSingleOrNull()
    }
}