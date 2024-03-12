package com.socoolheeya.webflux.member.adapter.out.persistence

import com.socoolheeya.webflux.member.domain.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository: JpaRepository<Member, Long> {
}