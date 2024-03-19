package com.socoolheeya.webflux.member.application.port.`in`

import com.socoolheeya.webflux.member.adapter.out.external.MemberResponse


fun interface LoadMemberUseCase {
    fun load(memberId: Long): MemberResponse.Companion.Search
}