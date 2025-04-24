package com.orca.player.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(val status: HttpStatus? = HttpStatus.NOT_FOUND, val message: String) {
    UNDEFINED_EXCEPTION(status = HttpStatus.INTERNAL_SERVER_ERROR, message = "Sorry, undefined exception"),
    PLAYER_NOT_FOUND(message = "Player not found."),
    DUPLICATE_PLAYER(status = HttpStatus.BAD_REQUEST, message = "Player (name, birth) duplicated"),

    // external
    REDIS_KEY_NOT_FOUND(message = "Key does not exist in Redis"),
    JSON_KEY_NOT_FOUND(message = "Invalid json key"),
}