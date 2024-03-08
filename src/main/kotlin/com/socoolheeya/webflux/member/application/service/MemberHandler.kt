package com.socoolheeya.webflux.member.application.service

import com.socoolheeya.webflux.member.adpater.out.external.MemberRequest
import kotlinx.coroutines.runBlocking
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerSentEvent
import org.springframework.stereotype.Component
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitFormData
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.sse
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono


@Component
class MemberHandler(
    private val memberService: MemberService
) {
    suspend fun searchMember(request: ServerRequest): ServerResponse {
        return ServerResponse.ok().sse().contentType(MediaType.APPLICATION_JSON)
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