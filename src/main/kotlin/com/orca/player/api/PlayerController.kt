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
                    birth = request.birth,
                    loginId = request.loginId,
                    password = request.password
                )
            )
        )
    }

    @GetMapping
    suspend fun get(@RequestParam id: String): ResponseEntity<PlayerResponse> {
        return baseResponse(
            body = PlayerResponse(playerService.get(id))
        )
    }

    @GetMapping("/credentials")
    suspend fun verify(@RequestParam loginId: String): ResponseEntity<VerifyResponse> {
        val player = playerService.verify(loginId)
        return baseResponse(
            body = VerifyResponse(
                id = player.id!!,
                loginId = player.loginId,
                encryptedPassword = player.password
            )
        )
    }
}