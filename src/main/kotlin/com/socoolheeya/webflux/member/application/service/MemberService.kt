package com.socoolheeya.webflux.member.application.service

import com.socoolheeya.webflux.member.adapter.out.external.MemberRequest
import com.socoolheeya.webflux.member.adapter.out.external.MemberResponse
import com.socoolheeya.webflux.member.application.port.`in`.LoadMemberUseCase
import com.socoolheeya.webflux.member.application.port.`in`.ModifyMemberUseCase
import com.socoolheeya.webflux.member.application.port.`in`.RegisterMemberUseCase
import com.socoolheeya.webflux.member.application.port.`in`.RemoveMemberUseCase
import com.socoolheeya.webflux.member.application.port.out.LoadMemberPort
import com.socoolheeya.webflux.member.application.port.out.ModifyMemberPort
import com.socoolheeya.webflux.member.application.port.out.RegisterMemberPort
import com.socoolheeya.webflux.member.application.port.out.RemoveMemberPort
import com.socoolheeya.webflux.member.domain.Member
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import java.util.*

@Service
@Validated
class MemberService(
    @Autowired @Qualifier("memberPersistenceAdapter")
    private val loadMemberPort: LoadMemberPort,
    @Autowired @Qualifier("memberPersistenceAdapter")
    private val registerMemberPort: RegisterMemberPort,
    @Autowired @Qualifier("memberPersistenceAdapter")
    private val modifyMemberPort: ModifyMemberPort,
    @Autowired @Qualifier("memberPersistenceAdapter")
    private val removeMemberPort: RemoveMemberPort
): LoadMemberUseCase, RegisterMemberUseCase, ModifyMemberUseCase, RemoveMemberUseCase {

    override fun load(memberId: Long): MemberResponse.Companion.Search {
        return loadMemberPort.loadMember(memberId)
    }

    override fun register(request: MemberRequest.Companion.Register): MemberResponse.Companion.Register {
        return registerMemberPort.registerMember(request)
    }

    override fun modify(memberId: Long, request: MemberRequest.Companion.Modify): MemberResponse.Companion.Modify {
        return modifyMemberPort.modifyMember(request)
    }

    override fun modifyPassword(request: MemberRequest.Companion.ModifyPassword) {
        modifyMemberPort.modifyPassword(request)
    }

    override fun remove(memberId: Long) {
        removeMemberPort.removeMember(memberId)
    }


    suspend fun getMembers(): MutableList<Member> {
        return (1..100000L).asSequence().map {
            Member(memberId = it,
                name = getName(it),
                email = getEmail(it),
                password = UUID.randomUUID().toString(),
                isDelete = it % 2 == 0L)
        }.toMutableList()
    }

    private fun getName(seq: Long): String {
        if(seq % 2 == 0L) {
            return "hong_$seq"
        }
        return "park_${seq + 100}"
    }

    private fun getEmail(seq: Long): String {
        if(seq % 2 == 0L) {
            return "hong_$seq@gmail.com"
        }
        return "park_${seq + 100}@gmail.com"
    }
}