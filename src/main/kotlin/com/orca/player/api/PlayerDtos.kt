package com.orca.player.api

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "선수 등록 RequestDTO")
data class GenerateRequest(
    @field:Schema(description = "이름")
    val name: String,
    @field:Schema(description = "생년월일")
    val birth: String,
    @field:Schema(description = "로그인 ID")
    val loginId: String,
    @field:Schema(description = "암호")
    val password: String
)

@Schema(description = "Player ResponseDTO")
data class PlayerResponse(
    @field:Schema(description = "Player ID")
    val id: String,
    @field:Schema(description = "이름")
    val name: String,
    @field:Schema(description = "생년월일")
    val birth: String,
    @field:Schema(description = "로그인 ID")
    val loginId: String,
    @field:Schema(description = "클럽 가입 목록")
    val clubHistories: List<ClubHistoryResponse>,
)

@Schema(description = "클럽 History")
data class ClubHistoryResponse(
    @field:Schema(description = "Club ID")
    val id: String,
    @field:Schema(description = "참여 경기 수")
    val matchCount: Int,
    @field:Schema(description = "득점")
    val goal: Int,
    @field:Schema(description = "도움")
    val assist: Int,
    @field:Schema(description = "MOM")
    val momCount: Int,
    @field:Schema(description = "가입 상태")
    val status: String,
)

@Schema(description = "로그인 검증 ResponseDTO")
data class VerifyResponse(
    @field:Schema(description = "Player ID")
    val id: String,
    @field:Schema(description = "로그인 ID")
    val loginId: String,
    @field:Schema(description = "암호 (Encrypted)")
    val encryptedPassword: String
)

enum class JoinApplicationStatus {
    PENDING,
    ACCEPTED,
    REJECTED
}

@Schema(description = "선수 정보 수정 RequestDTO")
data class UpdateRequest(
    @field:Schema(description = "이름")
    val name: String
)