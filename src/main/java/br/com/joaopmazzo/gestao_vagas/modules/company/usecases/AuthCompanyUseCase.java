package br.com.joaopmazzo.gestao_vagas.modules.company.usecases;

import br.com.joaopmazzo.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.joaopmazzo.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.joaopmazzo.gestao_vagas.modules.company.repositories.CompanyRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthCompanyUseCase {

    @Value("${security.token.secret}")
    private String secretKey;

    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    public String execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        CompanyEntity company = companyRepository
                .findByUsername(authCompanyDTO.getUsername())
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("Username/password incorrect");
                });

        boolean isPasswordMatches = passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());
        if (!isPasswordMatches) throw new AuthenticationException();

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create().withIssuer("javagas")
                .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                .withSubject(company.getId().toString())
                .sign(algorithm);
    }

}