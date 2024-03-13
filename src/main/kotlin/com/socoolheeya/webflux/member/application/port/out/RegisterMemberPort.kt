package com.socoolheeya.webflux.member.application.port.out

import com.socoolheeya.webflux.member.adapter.out.external.MemberRequest
import com.socoolheeya.webflux.member.adapter.out.external.MemberResponse

fun interface RegisterMemberPort {
    fun registerMember(request: MemberRequest.Companion.Register): MemberResponse.Companion.Register
}