package br.com.joaopmazzo.gestao_vagas.modules.company.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.joaopmazzo.gestao_vagas.exceptions.UserFoundException;
import br.com.joaopmazzo.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.joaopmazzo.gestao_vagas.modules.company.repositories.CompanyRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CreateCompanyUseCase {

    private CompanyRepository companyRepository;
    
    public CompanyEntity execute(CompanyEntity companyEntity) {
        companyRepository
            .findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
            .ifPresent((user) -> {
                throw new UserFoundException();
            });
        return companyRepository.save(companyEntity);
    }

}
