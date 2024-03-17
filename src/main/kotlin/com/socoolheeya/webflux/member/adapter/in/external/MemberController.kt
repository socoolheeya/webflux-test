package com.socoolheeya.webflux.member.adapter.`in`.external

import com.socoolheeya.webflux.member.adapter.out.external.MemberResponse
import com.socoolheeya.webflux.member.application.service.MemberService
import com.socoolheeya.webflux.member.domain.Member
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/v1/members")
class MemberController(
    private val memberService: MemberService
) {

    @GetMapping("")
    suspend fun getMembers(): MutableList<Member> {
        return memberService.getMembers()
    }

    @GetMapping("/{memberId}")
    suspend fun getNewMember(@PathVariable memberId: Long): MemberResponse.Companion.Search {
        return memberService.load(memberId)
    }
}