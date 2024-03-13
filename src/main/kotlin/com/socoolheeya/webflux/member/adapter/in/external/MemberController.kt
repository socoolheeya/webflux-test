package com.socoolheeya.webflux.member.adapter.`in`.external

import com.socoolheeya.webflux.member.adapter.out.external.MemberResponse
import com.socoolheeya.webflux.member.application.service.MemberService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/members")
class MemberController(
    private val memberService: MemberService
) {

    @GetMapping("/{memberId}")
    fun getNewMember(@PathVariable memberId: Long): MemberResponse.Companion.Search {
        return memberService.load(memberId)
    }
}