package com.orca.player.api

import com.orca.player.domain.Player

data class GenerateRequest(
    val name: String,
    val birth: String
)

data class GenerateResponse(
    val id: String,
    val name: String,
    val birth: String
) {
    constructor(player: Player) : this(
        id = player.id!!,
        name = player.name,
        birth = player.birth,
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
        clubHistories = player.histories.map { it.id }
    )
}