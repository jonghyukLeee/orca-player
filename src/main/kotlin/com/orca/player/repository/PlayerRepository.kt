package com.orca.player.repository

import com.orca.player.domain.Player
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface PlayerRepository: ReactiveMongoRepository<Player, ObjectId> {
    fun findByNameAndBirth(name: String, birth: String): Mono<Player>
    fun findByLoginId(loginId: String): Mono<Player>
}