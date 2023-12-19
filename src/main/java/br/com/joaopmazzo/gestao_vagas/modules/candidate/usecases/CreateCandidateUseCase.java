package br.com.joaopmazzo.gestao_vagas.modules.candidate.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.joaopmazzo.gestao_vagas.exceptions.UserFoundException;
import br.com.joaopmazzo.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.joaopmazzo.gestao_vagas.modules.candidate.CandidateRepository;

@Service
@RequiredArgsConstructor
public class CreateCandidateUseCase {

    private final CandidateRepository candidateRepository;

    public CandidateEntity execute(CandidateEntity candidateEntity) {
        candidateRepository
                .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });
        return candidateRepository.save(candidateEntity);
    }

}
