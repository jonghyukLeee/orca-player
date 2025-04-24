package com.orca.player.utils

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun getCurrentTimestamp(): String {
    return LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
}

fun <T> baseResponse(status: HttpStatus? = HttpStatus.OK, body: T): ResponseEntity<T> {
    return ResponseEntity.status(status!!).body(body)
}

fun buildQueryById(id: ObjectId): Query {
    return Query(Criteria.where("_id").`is`(id))
}