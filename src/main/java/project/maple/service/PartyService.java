package project.maple.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.maple.domain.Party;
import project.maple.repository.PartyRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PartyService {

    private final PartyRepository partyRepository;

    public Party createParty(Party party) {
        return partyRepository.save(party);
    }
}
