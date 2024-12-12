package project.maple.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.maple.domain.PartyMember;

import java.util.List;
import java.util.Optional;

public interface PartyMemberRepository extends JpaRepository<PartyMember, Long> {

    // 특정 멤버가 특정 파티에 이미 신청 했는지 여부 확인
    boolean existsByPartyIdAndMemberId(Long partyId, Long memberId);

    // 파티에 속한 멤버 찾기
    Optional<PartyMember> findByPartyIdAndMemberId(Long partyId, Long memberId);
}
