package com.orca.player.external.club

import com.orca.player.api.JoinApplicationStatus
import com.orca.player.external.WebClientFactory
import kotlinx.coroutines.reactive.awaitSingle
import org.bson.types.ObjectId
import org.springframework.stereotype.Component

@Component
class ClubService(
    clientFactory: WebClientFactory
) {
    private val client = clientFactory.getClient("clubs")

    suspend fun getPlayerApplications(playerId: ObjectId, status: JoinApplicationStatus): List<JoinApplicationResponse> {
        return client.get()
            .uri {
                it.path("/join-application")
                    .queryParam("playerId", playerId.toString())
                    .queryParam("status", status)
                    .build()
            }.retrieve()
            .bodyToFlux(JoinApplicationResponse::class.java)
            .collectList()
            .awaitSingle()
            .toList()
    }
}