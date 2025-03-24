package com.orca.player.external.club

import com.orca.player.api.JoinApplicationStatus
import com.orca.player.external.WebClientFactory
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Component

@Component
class ClubService(
    clientFactory: WebClientFactory
) {
    private val client = clientFactory.getClient("club")

    suspend fun getPlayerApplications(playerId: String, status: JoinApplicationStatus): List<JoinApplicationResponse> {
        return client.get()
            .uri {
                it.path("/join-application")
                    .queryParam("playerId", playerId)
                    .queryParam("status", status)
                    .build()
            }.retrieve()
            .bodyToFlux(JoinApplicationResponse::class.java)
            .collectList()
            .awaitSingle()
            .toList()
    }
}