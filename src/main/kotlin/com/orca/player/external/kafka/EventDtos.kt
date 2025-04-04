package com.orca.player.external.kafka

data class JoinAcceptMessage(
    val applicationId: String,
    val clubId: String,
    val playerId: String,
    val position:Position,
    val status:JoinApplicationStatus,
) {
    enum class Position {
        FW,
        MF,
        DF
    }

    enum class JoinApplicationStatus {
        PENDING,
        ACCEPTED,
        REJECTED
    }
}

data class PlayerUpdateMessage(
    val id: String,
    val name: String,
    val birth: String,
    val loginId: String,
)