package br.com.joaopmazzo.gestao_vagas.modules.candidate.usecases;

import br.com.joaopmazzo.gestao_vagas.exceptions.JobNotFoundException;
import br.com.joaopmazzo.gestao_vagas.exceptions.UserNotFoundException;
import br.com.joaopmazzo.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.joaopmazzo.gestao_vagas.modules.company.repositories.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplyJobCandidateUseCase {

    private final CandidateRepository candidateRepository;
    private final JobRepository jobRepository;

    public void execute(UUID idCandidate, UUID idJob) {
        candidateRepository.findById(idCandidate).orElseThrow(UserNotFoundException::new);
        jobRepository.findById(idJob).orElseThrow(JobNotFoundException::new);


    }

}
