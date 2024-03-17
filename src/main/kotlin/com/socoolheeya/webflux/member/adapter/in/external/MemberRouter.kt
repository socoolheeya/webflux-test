package com.socoolheeya.webflux.member.adapter.`in`.external

import com.socoolheeya.webflux.member.application.service.MemberHandler
import kotlinx.coroutines.FlowPreview
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class MemberRouter {

    companion object {
        const val MEMBER_PATH = "/members/{memberId}"
    }

    @Bean
    @FlowPreview
    fun memberEndpoint(memberHandler: MemberHandler) =
        coRouter {
            accept(MediaType.TEXT_EVENT_STREAM).nest {
                GET(MEMBER_PATH).invoke(memberHandler::searchMember)
                POST("/members", memberHandler::registerMember)
                PUT(MEMBER_PATH, memberHandler::modifyMember)
                DELETE(MEMBER_PATH, memberHandler::deleteMember)
            }
        }
}