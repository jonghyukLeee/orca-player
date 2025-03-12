package com.orca.player.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "players")
data class Player(
    @Id
    var id: String? = null,
    val name: String,
    var birth: String,
    var histories: MutableList<ClubHistory> = mutableListOf()
)

data class ClubHistory(
    var id: String,
    var matchCount: Int? = 0,
    var goal: Int? = 0,
    var assist: Int? = 0,
    var momCount: Int? = 0,
    var status: ClubStatus,
)

enum class ClubStatus {
    ACTIVE,
    INACTIVE,
    WITHDRAWN
}