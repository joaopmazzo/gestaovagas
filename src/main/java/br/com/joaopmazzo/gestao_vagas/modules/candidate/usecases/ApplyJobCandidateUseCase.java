package br.com.joaopmazzo.gestao_vagas.modules.candidate.usecases;

import br.com.joaopmazzo.gestao_vagas.exceptions.JobNotFoundException;
import br.com.joaopmazzo.gestao_vagas.exceptions.UserNotFoundException;
import br.com.joaopmazzo.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import br.com.joaopmazzo.gestao_vagas.modules.candidate.repositories.ApplyJobRepository;
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
    private final ApplyJobRepository applyJobRepository;

    public ApplyJobEntity execute(UUID idCandidate, UUID idJob) {
        candidateRepository.findById(idCandidate).orElseThrow(UserNotFoundException::new);
        jobRepository.findById(idJob).orElseThrow(JobNotFoundException::new);

        ApplyJobEntity applyJob = ApplyJobEntity.builder()
                .candidateId(idCandidate)
                .jobId(idJob)
                .build();
        return applyJobRepository.save(applyJob);
    }

}
