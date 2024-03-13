package com.socoolheeya.webflux.member.application.service

import com.socoolheeya.webflux.member.adapter.out.external.MemberRequest
import kotlinx.coroutines.FlowPreview
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerSentEvent
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitFormData
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.json
import org.springframework.web.reactive.function.server.sse


@Component
class MemberHandler(
    private val memberService: MemberService
) {
    @FlowPreview
    suspend fun searchMember(request: ServerRequest): ServerResponse {
        return ServerResponse.ok()
            .sse()
            .bodyValueAndAwait(ServerSentEvent.builder(
                memberService.load(request.pathVariable("memberId").toLong())
            ))
    }

    suspend fun registerMember(request: ServerRequest): ServerResponse {
        val data = request.awaitFormData().toSingleValueMap()
        val requestBody = MemberRequest.Companion.Register(
            name = data["name"]?: "",
            email = data["email"],
            password = data["password"]?: "",
            isDelete = false
        )
        return ServerResponse.ok().sse().contentType(MediaType.APPLICATION_JSON)
            .bodyValueAndAwait(ServerSentEvent.builder(
                memberService.register(requestBody)
            ))
    }

    suspend fun modifyMember(request: ServerRequest): ServerResponse {
        val formData = request.awaitFormData()
            .toSingleValueMap()

        val requestBody = MemberRequest.Companion.Modify(
            memberId = formData["memberId"]?.toLong()?: 0L,
            name = formData["name"]?: "",
            email = formData["email"],
            isDelete = false
        )

        return ServerResponse.ok().sse().contentType(MediaType.APPLICATION_JSON)
            .bodyValueAndAwait(ServerSentEvent.builder(
                memberService.modify(request.pathVariable("memberId").toLong(), requestBody)
            ).build())
    }

    suspend fun deleteMember(request: ServerRequest): ServerResponse {
        return ServerResponse.ok().sse().contentType(MediaType.APPLICATION_JSON)
            .bodyValueAndAwait(ServerSentEvent.builder(
                memberService.remove(request.pathVariable("memberId").toLong())
            ).build())
    }
}