package project.maple.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.maple.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
