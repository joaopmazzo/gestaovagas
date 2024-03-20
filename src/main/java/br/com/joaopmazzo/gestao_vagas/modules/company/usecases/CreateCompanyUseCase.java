package br.com.joaopmazzo.gestao_vagas.modules.company.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.joaopmazzo.gestao_vagas.exceptions.UserFoundException;
import br.com.joaopmazzo.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.joaopmazzo.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
@RequiredArgsConstructor
public class CreateCompanyUseCase {

    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    public CompanyEntity execute(CompanyEntity companyEntity) {
        companyRepository
                .findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });

        var passwordEcripted = passwordEncoder.encode(companyEntity.getPassword());
        companyEntity.setPassword(passwordEcripted);

        return companyRepository.save(companyEntity);
    }

}
