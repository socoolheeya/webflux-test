package com.socoolheeya.webflux.member.application.port.out

interface RemoveMemberPort {
    fun removeMember(memberId: Long)
}