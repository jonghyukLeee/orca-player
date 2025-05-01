package com.orca.player.external.kafka

data class JoinAcceptMessage(
    val applicationId: String?,
    val clubId: String,
    val playerId: String
)

data class PlayerMessage(
    val id: String,
    val name: String,
    val birth: String,
    val loginId: String,
)

data class ClubCreatedMessage(
    val clubId: String,
    val playerId: String
)