package com.orca.player.api

import com.orca.player.domain.toResponse
import com.orca.player.external.club.JoinApplicationResponse
import com.orca.player.service.PlayerService
import com.orca.player.utils.baseResponse
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping
@RestController
class PlayerController(
    private val playerService: PlayerService
) {
    @Operation(
        summary = "선수 등록",
        description = "선수 등록 (회원가입) API"
    )
    @PostMapping
    suspend fun generate(@RequestBody request: GenerateRequest): ResponseEntity<PlayerResponse> {
        return baseResponse(
            body =
            playerService.generate(
                name = request.name,
                birth = request.birth,
                loginId = request.loginId,
                password = request.password
            ).toResponse()
        )
    }

    @Operation(
        summary = "선수 조회",
        description = "Player 단건 조회 API"
    )
    @GetMapping("/{playerId}")
    suspend fun getPlayer(@PathVariable playerId: String): ResponseEntity<PlayerResponse> {
        return baseResponse(
            body = playerService.getPlayer(playerId).toResponse()
        )
    }

    @Operation(
        summary = "선수 수정",
        description = "선수 정보 수정 API"
    )
    @PatchMapping("/{playerId}")
    suspend fun update(
        @PathVariable playerId: String,
        @RequestBody request: UpdateRequest
    ): ResponseEntity<PlayerResponse> {
        return baseResponse(
            body = playerService.update(playerId, request.name).toResponse()
        )
    }

    @Operation(
        summary = "회원 검증",
        description = "회원 검증 API"
    )
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

    @Operation(
        summary = "가입 신청 목록 조회",
        description = "Player가 가입 신청한 클럽 목록 조회 API"
    )
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