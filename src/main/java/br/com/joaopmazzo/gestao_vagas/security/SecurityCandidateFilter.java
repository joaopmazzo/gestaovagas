package br.com.joaopmazzo.gestao_vagas.security;

import br.com.joaopmazzo.gestao_vagas.providers.JWTCandidateProvider;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SecurityCandidateFilter extends OncePerRequestFilter {

    private final JWTCandidateProvider jwtCandidateProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
//        SecurityContextHolder.clearContext(); // serve para limpar sujeiras
        String header = request.getHeader("Authorization");

        if (request.getRequestURI().startsWith("/candidate") && Objects.nonNull(header)) {
            DecodedJWT subjectToken = jwtCandidateProvider.validateToken(header);

            if (Objects.isNull(subjectToken)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            request.setAttribute("candidate_id", subjectToken.getSubject());

            List<String> roles = subjectToken.getClaim("roles").asList(String.class);
            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE" + role.toUpperCase()))
                    .toList();

            // Serve para o spring sempre validar se o usuario está autenticado e se possui as roles
            // necessárias
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    subjectToken.getSubject(), null, authorities
            );

            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
}
