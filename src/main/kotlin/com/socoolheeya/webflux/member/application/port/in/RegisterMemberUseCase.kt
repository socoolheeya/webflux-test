package com.socoolheeya.webflux.member.application.port.`in`

import com.socoolheeya.webflux.member.adpater.out.external.MemberRequest
import com.test.springdocstest.member.adapter.out.external.MemberResponse

interface RegisterMemberUseCase {
    fun register(request: MemberRequest.Companion.Register): MemberResponse.Companion.Register
}