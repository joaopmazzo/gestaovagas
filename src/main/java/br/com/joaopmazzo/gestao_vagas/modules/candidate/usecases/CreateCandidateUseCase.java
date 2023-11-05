package br.com.joaopmazzo.gestao_vagas.modules.candidate.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.joaopmazzo.gestao_vagas.exceptions.UserFoundException;
import br.com.joaopmazzo.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.joaopmazzo.gestao_vagas.modules.candidate.CandidateRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CreateCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;
    
    public CandidateEntity execute(CandidateEntity candidateEntity) {
        candidateRepository
            .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
            .ifPresent((user) -> {
                throw new UserFoundException();
            });
        return candidateRepository.save(candidateEntity);
    }

}
