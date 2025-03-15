package com.orca.player.api

import com.orca.player.domain.Player

data class GenerateRequest(
    val name: String,
    val birth: String,
    val loginId: String,
    val password: String
)

data class GenerateResponse(
    val id: String,
    val name: String,
    val birth: String,
    val loginId: String
) {
    constructor(player: Player) : this(
        id = player.id!!,
        name = player.name,
        birth = player.birth,
        loginId = player.loginId
    )
}

data class PlayerResponse(
    val id: String,
    val name: String,
    val birth: String,
    val clubHistories: List<String>,
) {
    constructor(player: Player) : this(
        id = player.id!!,
        name = player.name,
        birth = player.birth,
        clubHistories = player.clubHistories.map { it.id }
    )
}

data class VerifyResponse(
    val id: String,
    val loginId: String,
    val encryptedPassword: String
)