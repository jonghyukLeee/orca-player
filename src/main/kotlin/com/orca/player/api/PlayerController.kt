package com.orca.player.api

import com.orca.player.external.club.JoinApplicationResponse
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

    @GetMapping("/{playerId}")
    suspend fun getPlayer(@PathVariable playerId: String): ResponseEntity<PlayerResponse> {
        return baseResponse(
            body = PlayerResponse(playerService.getPlayer(playerId))
        )
    }

    @PatchMapping("/{playerId}")
    suspend fun update(
        @PathVariable playerId: String,
        @RequestBody request: UpdateRequest
    ): ResponseEntity<PlayerResponse> {
        return baseResponse(
            body = PlayerResponse(
                playerService.update(
                    playerId, request.name
                )
            )
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

    @GetMapping("/{playerId}/join-application")
    suspend fun getJoinApplications(
        @PathVariable playerId: String,
        @RequestParam status: JoinApplicationStatus
    ): ResponseEntity<List<JoinApplicationResponse>> {
        return baseResponse(
            body = playerService.getJoinApplications(playerId, status)
        )
    }
}