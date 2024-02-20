package br.com.joaopmazzo.gestao_vagas.modules.candidate.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import br.com.joaopmazzo.gestao_vagas.exceptions.JobNotFoundException;
import br.com.joaopmazzo.gestao_vagas.exceptions.UserNotFoundException;
import br.com.joaopmazzo.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import br.com.joaopmazzo.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.joaopmazzo.gestao_vagas.modules.candidate.repositories.ApplyJobRepository;
import br.com.joaopmazzo.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.joaopmazzo.gestao_vagas.modules.company.entities.JobEntity;
import br.com.joaopmazzo.gestao_vagas.modules.company.repositories.JobRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {

    @InjectMocks
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @Mock
    private CandidateRepository candidateRepository;
    @Mock
    private JobRepository jobRepository;
    @Mock
    private ApplyJobRepository applyJobRepository;

    @Test
    @DisplayName("Should not be able to apply job with candidate not found")
    public void should_not_be_able_to_apply_job_with_candidate_not_found() {
        try {
            applyJobCandidateUseCase.execute(null, null);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(UserNotFoundException.class);
        }
    }

    @Test
    @DisplayName("Should not be able to apply job with job not found")
    public void should_not_be_able_to_apply_job_with_job_not_found() {
        UUID idCandidate = UUID.randomUUID();
        CandidateEntity candidate = CandidateEntity.builder()
                .id(idCandidate)
                .build();
        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.ofNullable(candidate));

        try {
            applyJobCandidateUseCase.execute(idCandidate, null);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(JobNotFoundException.class);
        }
    }

    @Test
    @DisplayName("Should be able to create a new apply job")
    public void should_be_able_to_create_a_new_apply_job() {
        UUID idCandidate = UUID.randomUUID();
        UUID idJob = UUID.randomUUID();

        ApplyJobEntity applyJob = ApplyJobEntity.builder()
                .candidateId(idCandidate)
                .jobId(idJob)
                .build();

        ApplyJobEntity applyJobCreated = ApplyJobEntity.builder()
                .id(UUID.randomUUID())
                .candidateId(idCandidate)
                .jobId(idJob)
                .build();

        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(new CandidateEntity()));
        when(jobRepository.findById(idJob)).thenReturn(Optional.of(new JobEntity()));
        when(applyJobRepository.save(applyJob)).thenReturn(applyJobCreated);

        ApplyJobEntity result = applyJobCandidateUseCase.execute(idCandidate, idJob);

        assertThat(result).hasFieldOrProperty("id");
        assertNotNull(result.getId());
    }
}
