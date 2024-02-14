package br.com.joaopmazzo.gestao_vagas.modules.company.usecases;

import br.com.joaopmazzo.gestao_vagas.modules.company.dto.AuthCompanyRequestDTO;
import br.com.joaopmazzo.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.joaopmazzo.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.joaopmazzo.gestao_vagas.security.AuthResponseDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthCompanyUseCase {

    @Value("${security.token.secret.company}")
    private String secretKey;

    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthResponseDTO execute(AuthCompanyRequestDTO authCompanyRequestDTO) throws AuthenticationException {
        CompanyEntity company = companyRepository
                .findByUsername(authCompanyRequestDTO.username())
                .orElseThrow(() -> new UsernameNotFoundException("Username/password incorrect"));

        boolean isPasswordMatches = passwordEncoder.matches(authCompanyRequestDTO.password(), company.getPassword());
        if (!isPasswordMatches) throw new AuthenticationException();

        var expiresIn = Instant.now().plus(Duration.ofHours(2));

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String token = JWT.create().withIssuer("javagas")
                .withExpiresAt(expiresIn)
                .withClaim("roles", List.of("COMPANY"))
                .withSubject(company.getId().toString())
                .sign(algorithm);

        return AuthResponseDTO.builder()
                .access_token(token)
                .expires_in(expiresIn.toEpochMilli())
                .build();
    }

}