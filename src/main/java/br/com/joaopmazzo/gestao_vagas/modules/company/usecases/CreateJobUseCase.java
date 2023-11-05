package br.com.joaopmazzo.gestao_vagas.modules.company.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.joaopmazzo.gestao_vagas.modules.company.entities.JobEntity;
import br.com.joaopmazzo.gestao_vagas.modules.company.repositories.JobRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CreateJobUseCase {

    private JobRepository jobRepository;

    public JobEntity execute(JobEntity jobEntity) {
        return jobRepository.save(jobEntity);
    }
    
}
