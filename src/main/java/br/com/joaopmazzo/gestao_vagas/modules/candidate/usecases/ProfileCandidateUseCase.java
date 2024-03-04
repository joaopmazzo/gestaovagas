package br.com.joaopmazzo.gestao_vagas.modules.candidate.usecases;

import br.com.joaopmazzo.gestao_vagas.exceptions.UserNotFoundException;
import br.com.joaopmazzo.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.joaopmazzo.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.joaopmazzo.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.joaopmazzo.gestao_vagas.modules.candidate.mapper.CandidateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileCandidateUseCase {

    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;

    public ProfileCandidateResponseDTO execute(UUID idCandidate) {
        CandidateEntity candidate = this.candidateRepository
                .findById(idCandidate)
                .orElseThrow(UserNotFoundException::new);


        return candidateMapper.candidateToProfileCandidateDTO(candidate);
    }

}
