package com.orca.player.external.kafka

import com.orca.player.domain.Player
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import org.springframework.stereotype.Service

@Service
class PlayerEventPublisher(
    private val reactiveKafkaProducerTemplate: ReactiveKafkaProducerTemplate<String, Any>
) {
    suspend fun playerUpdate(updatedPlayer: Player) {
        send("player-update",
            PlayerUpdateMessage(
                id = updatedPlayer.id!!,
                name = updatedPlayer.name,
                birth = updatedPlayer.birth,
                loginId = updatedPlayer.loginId,
            )
        )
    }

    private suspend fun send(topic: String, message: Any) {
        reactiveKafkaProducerTemplate.send(topic, message).awaitSingle()
    }
}