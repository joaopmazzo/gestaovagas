package br.com.joaopmazzo.gestao_vagas.modules.candidate.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.joaopmazzo.gestao_vagas.exceptions.UserFoundException;
import br.com.joaopmazzo.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.joaopmazzo.gestao_vagas.modules.candidate.repositories.CandidateRepository;

@Service
@RequiredArgsConstructor
public class CreateCandidateUseCase {

    private final CandidateRepository candidateRepository;
    private final PasswordEncoder passwordEncoder;

    public CandidateEntity execute(CandidateEntity candidateEntity) {
        candidateRepository
                .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });

        var passwordEncripted = passwordEncoder.encode(candidateEntity.getPassword());
        candidateEntity.setPassword(passwordEncripted);

        return candidateRepository.save(candidateEntity);
    }

}
