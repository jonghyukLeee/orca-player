package com.orca.player.repository

import com.orca.player.domain.Player
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface PlayerRepository: ReactiveMongoRepository<Player, String> {
    fun findByNameAndBirth(name: String, birth: String): Mono<Player>
}