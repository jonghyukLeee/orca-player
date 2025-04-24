package com.orca.player.domain

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "players")
data class Player(
    @Id
    var id: ObjectId? = null,
    val name: String,
    var birth: String,
    var loginId: String,
    var password: String,
    var clubHistories: MutableList<ClubHistory> = mutableListOf()
) {
    data class ClubHistory(
        val clubId: ObjectId,
        var matchCount: Int = 0,
        var goal: Int = 0,
        var assist: Int = 0,
        var momCount: Int = 0,
        var status: ActiveStatus = ActiveStatus.ACTIVE,
    )

    enum class ActiveStatus {
        ACTIVE,
        INACTIVE,
        WITHDRAWN
    }
}
