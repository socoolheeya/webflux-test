package com.socoolheeya.webflux.member.application.port.out

import com.socoolheeya.webflux.member.adpater.out.external.MemberRequest
import com.test.springdocstest.member.adapter.out.external.MemberResponse

interface RegisterMemberPort {
    fun registerMember(request: MemberRequest.Companion.Register): MemberResponse.Companion.Register
}