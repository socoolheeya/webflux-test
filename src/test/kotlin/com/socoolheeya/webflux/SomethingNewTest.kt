package com.socoolheeya.webflux

import com.socoolheeya.webflux.member.adpater.`in`.external.MemberRouter
import com.socoolheeya.webflux.member.application.service.MemberHandler
import com.socoolheeya.webflux.member.application.service.MemberService
import com.test.springdocstest.member.adapter.out.external.MemberResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito
import org.mockito.BDDMockito.*
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient


@ExtendWith(value = [SpringExtension::class])
@WebFluxTest
class SomethingNewTest {
    private lateinit var webTestClient: WebTestClient
    @MockBean lateinit var memberService: MemberService

    @BeforeEach
    fun setUp() {
        val routerFunction = MemberRouter().memberEndpoint(MemberHandler(memberService))
        webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build()
    }

    @Test
    @DisplayName("멤버 조회 테스트")
    fun getMemberTest() {
        given(memberService.load(1L)).willReturn(
            MemberResponse.Companion.Search(
                memberId = 1L,
                name = "hong",
                email = "hong@gmail.com",
                isDelete = false
            )
        )

        val responseBody = webTestClient.get()
            .uri("/members/{memberId}", 1L)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(MemberResponse.Companion.Search::class.java)
            .hasSize(1)
            .returnResult().responseBody

        assert(responseBody?.get(0)?.name.equals("hong"))
    }

    fun test2() {

    }
}