package com.orca.player.api

import com.orca.player.service.PlayerService
import com.orca.player.utils.baseResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping
@RestController
class PlayerController(
    private val playerService: PlayerService
) {
    @PostMapping
    suspend fun generate(@RequestBody request: GenerateRequest): ResponseEntity<GenerateResponse> {
        return baseResponse(
            body = GenerateResponse(
                playerService.generate(
                    name = request.name,
                    birth = request.birth
                )
            )
        )
    }

    @GetMapping
    suspend fun findOne(@RequestParam id: String): ResponseEntity<PlayerResponse> {
        return baseResponse(
            body = PlayerResponse(playerService.getById(id))
        )
    }
}