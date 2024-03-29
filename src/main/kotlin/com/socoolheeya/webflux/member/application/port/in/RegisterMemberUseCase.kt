package com.socoolheeya.webflux.member.application.port.`in`

import com.socoolheeya.webflux.member.adapter.out.external.MemberRequest
import com.socoolheeya.webflux.member.adapter.out.external.MemberResponse

interface RegisterMemberUseCase {
    fun register(request: MemberRequest.Companion.Register): MemberResponse.Companion.Register
}