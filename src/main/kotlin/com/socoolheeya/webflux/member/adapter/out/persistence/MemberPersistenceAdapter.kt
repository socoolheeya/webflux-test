package com.socoolheeya.webflux.member.adapter.out.persistence

import com.socoolheeya.webflux.member.adapter.out.external.MemberDto
import com.socoolheeya.webflux.member.adapter.out.external.MemberRequest
import com.socoolheeya.webflux.member.application.port.out.LoadMemberPort
import com.socoolheeya.webflux.member.application.port.out.ModifyMemberPort
import com.socoolheeya.webflux.member.application.port.out.RegisterMemberPort
import com.socoolheeya.webflux.member.application.port.out.RemoveMemberPort
import com.test.springdocstest.member.adapter.out.external.MemberResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component("MemberPersistenceAdapter")
@Transactional(readOnly = true)
class MemberPersistenceAdapter (
    private val memberRepository: MemberRepository
): LoadMemberPort, RegisterMemberPort, ModifyMemberPort, RemoveMemberPort {

    override fun loadMember(memberId: Long): MemberResponse.Companion.Search {
        return memberRepository.findByIdOrNull(memberId)?.toResponseSearchDomain()?:
        MemberResponse.Companion.Search(0L, "no name", "no email", false)
    }

    @Transactional
    override fun registerMember(request: MemberRequest.Companion.Register): MemberResponse.Companion.Register {
        return memberRepository.save(request.toEntity())
            .toResponseRegisterDomain()
    }

    @Transactional
    override fun modifyMember(request: MemberRequest.Companion.Modify): MemberResponse.Companion.Modify {
        val member = memberRepository.findById(request.getMemberId())
            .orElseThrow { throw IllegalArgumentException("can not found member") }
        member.update(
            MemberDto(
                memberId = request.getMemberId(),
                name = request.getName(),
                email = request.getEmail(),
                isDelete = request.isDelete()
            )
        )

        return member.toResponseModifyDomain()
    }

    @Transactional
    override fun modifyPassword(request: MemberRequest.Companion.ModifyPassword) {
        val member = memberRepository.findById(request.getMemberId())
            .orElseThrow { throw IllegalArgumentException("can not found member") }
        member.updatePassword(request.getPassword())

    }

    @Transactional
    override fun removeMember(memberId: Long) {
        memberRepository.deleteById(memberId)
    }


}