package com.socoolheeya.webflux.member.application.port.out

import com.test.springdocstest.member.adapter.out.external.MemberResponse

interface LoadMemberPort {
    fun loadMember(memberId: Long): MemberResponse.Companion.Search
}