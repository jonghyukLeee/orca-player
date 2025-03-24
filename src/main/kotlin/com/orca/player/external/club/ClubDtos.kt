package com.orca.player.external.club

import com.orca.player.api.JoinApplicationStatus

data class JoinApplicationResponse(
    val requestId: String,
    val clubId: String,
    val playerId: String,
    val status: JoinApplicationStatus,
    val notification: String,
    val createdAt: String
)