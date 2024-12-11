package project.maple.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.maple.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m where m.userEmail = :email")
    Optional<Member> findByEmail(@Param("email") String email);

    @Query("select m from Member m where m.apiKey = :apiKey")
    Optional<Member> findByApiKey(@Param("apiKey") String apiKey);
}
