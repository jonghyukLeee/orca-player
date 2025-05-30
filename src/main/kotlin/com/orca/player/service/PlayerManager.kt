package com.orca.player.service

import com.orca.player.domain.Player
import com.orca.player.exception.BaseException
import com.orca.player.exception.ErrorCode
import com.orca.player.repository.PlayerRepository
import com.orca.player.utils.buildQueryById
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Component

@Component
class PlayerManager(
    private val repository: PlayerRepository,
    private val reactiveMongoTemplate: ReactiveMongoTemplate
) {
    suspend fun create(name: String, birth: String, loginId: String, password: String): Player {
        return repository.save(
            Player(
                name = name, birth = birth, loginId = loginId, password = password
            )
        ).awaitSingle()
    }

    suspend fun update(playerId: ObjectId, name: String): Player {
        val update = Update().apply {
            set("name", name)
        }

        return reactiveMongoTemplate.findAndModify(
            buildQueryById(playerId)
            , update
            , FindAndModifyOptions().returnNew(true)
            , Player::class.java
        ).awaitSingleOrNull() ?: throw BaseException(ErrorCode.PLAYER_NOT_FOUND)
    }

    suspend fun addClub(playerId: ObjectId, clubId: ObjectId) {
        val update = Update().apply {
            addToSet(
                "clubHistories",
                Player.ClubHistory(
                    clubId = clubId
                )
            )
        }

        reactiveMongoTemplate.findAndModify(
            buildQueryById(playerId), update, Player::class.java
        ).awaitSingle()
    }

    suspend fun deleteClub(playerId: ObjectId, clubId: ObjectId) {
        val update = Update().apply {
            pull("clubHistories", buildQueryById(clubId))
        }

        reactiveMongoTemplate.findAndModify(
            buildQueryById(playerId)
            , update
            , Player::class.java
        )
    }


}