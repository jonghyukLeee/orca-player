package com.orca.player.exception

class ErrorResponse(
    val code: String,
    val message: String,
    val timestamp: String,

    ) {
    val serviceName = "player"

    constructor(ex: BaseException) : this(
        code = ex.code,
        message = ex.message,
        timestamp = ex.timeStamp,
    )
}