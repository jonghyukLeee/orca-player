package com.orca.player.domain

import com.orca.player.api.ClubHistoryResponse
import com.orca.player.api.PlayerResponse

fun Player.toResponse(): PlayerResponse {
    return PlayerResponse(
        id = this.id.toString(),
        name = this.name,
        birth = this.birth,
        loginId = this.loginId,
        clubHistories = this.clubHistories.map { it.toResponse() }
    )
}

fun Player.ClubHistory.toResponse(): ClubHistoryResponse {
    return ClubHistoryResponse(
        id = this.clubId.toString(),
        matchCount = this.matchCount,
        goal = this.goal,
        assist = this.assist,
        momCount = this.momCount,
        status = this.status.name,
    )
}