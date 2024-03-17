package com.socoolheeya.webflux

import com.socoolheeya.webflux.member.adapter.`in`.external.MemberRouter
import com.socoolheeya.webflux.member.adapter.out.external.MemberResponse
import com.socoolheeya.webflux.member.application.service.MemberHandler
import com.socoolheeya.webflux.member.application.service.MemberService
import com.socoolheeya.webflux.member.domain.Member
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import java.util.*


@ExtendWith(value = [SpringExtension::class])
@WebFluxTest
class MemberTest {
    private lateinit var webTestClient: WebTestClient
    @MockBean lateinit var memberService: MemberService

    @OptIn(FlowPreview::class)
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

    @Test
    fun test() {
        val response = memberService.load(1L)
        println(response)
    }

    @Test
    fun test2() {
        runBlocking {
            val response = memberService.getMembers()
            println(response)
        }

    }



    fun getMembers(): MutableList<Member> =
        (1..10000000L).asSequence().map {
            Member(memberId = it,
                name = getName(it),
                email = getEmail(it),
                password = UUID.randomUUID().toString(),
                isDelete = it % 2 == 0L)
        }.toMutableList()

    private fun getName(seq: Long): String {
        if(seq % 2 == 0L) {
            return "hong_$seq"
        }
        return "park_${seq + 100}"
    }

    private fun getEmail(seq: Long): String {
        if(seq % 2 == 0L) {
            return "hong_$seq@gmail.com"
        }
        return "park_${seq + 100}@gmail.com"
    }
}