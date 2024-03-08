package com.socoolheeya.webflux.member.application.port.`in`

import com.socoolheeya.webflux.member.adpater.out.external.MemberRequest
import com.test.springdocstest.member.adapter.out.external.MemberResponse

interface ModifyMemberUseCase {
    fun modify(memberId: Long, request: MemberRequest.Companion.Modify): MemberResponse.Companion.Modify
    fun modifyPassword(request: MemberRequest.Companion.ModifyPassword)
}