package br.com.joaopmazzo.gestao_vagas.modules.candidate.usecases;

import br.com.joaopmazzo.gestao_vagas.modules.company.entities.JobEntity;
import br.com.joaopmazzo.gestao_vagas.modules.company.repositories.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListAllJobsByFilterUseCase {

    private final JobRepository jobRepository;

    public List<JobEntity> execute(String description) {
        return jobRepository.findJobEntitiesByDescriptionContainingIgnoreCase(description);
    }

}
