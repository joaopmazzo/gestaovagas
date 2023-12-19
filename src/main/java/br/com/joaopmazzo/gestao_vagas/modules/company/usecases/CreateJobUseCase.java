package br.com.joaopmazzo.gestao_vagas.modules.company.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.joaopmazzo.gestao_vagas.modules.company.entities.JobEntity;
import br.com.joaopmazzo.gestao_vagas.modules.company.repositories.JobRepository;

@Service
@RequiredArgsConstructor
public class CreateJobUseCase {

    private final JobRepository jobRepository;

    public JobEntity execute(JobEntity jobEntity) {
        return jobRepository.save(jobEntity);
    }

}
