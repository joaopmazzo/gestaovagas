package br.com.joaopmazzo.gestao_vagas.modules.company.usecases;

import br.com.joaopmazzo.gestao_vagas.exceptions.CompanyNotFoundException;
import br.com.joaopmazzo.gestao_vagas.modules.company.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import br.com.joaopmazzo.gestao_vagas.modules.company.entities.JobEntity;
import br.com.joaopmazzo.gestao_vagas.modules.company.repositories.JobRepository;

@Service
@RequiredArgsConstructor
public class CreateJobUseCase {

    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;

    public JobEntity execute(JobEntity jobEntity) {
        companyRepository
                .findById(jobEntity.getCompanyId())
                .orElseThrow(CompanyNotFoundException::new);
        return jobRepository.save(jobEntity);
    }

}
