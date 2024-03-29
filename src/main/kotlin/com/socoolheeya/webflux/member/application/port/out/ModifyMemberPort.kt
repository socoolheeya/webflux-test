package com.socoolheeya.webflux.member.application.port.out

import com.socoolheeya.webflux.member.adapter.out.external.MemberRequest
import com.socoolheeya.webflux.member.adapter.out.external.MemberResponse

interface ModifyMemberPort {
    fun modifyMember(request: MemberRequest.Companion.Modify): MemberResponse.Companion.Modify

    fun modifyPassword(request: MemberRequest.Companion.ModifyPassword)
}