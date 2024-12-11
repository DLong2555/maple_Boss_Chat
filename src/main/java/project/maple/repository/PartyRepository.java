package project.maple.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.maple.domain.Party;

public interface PartyRepository extends JpaRepository <Party, Long> {

}
