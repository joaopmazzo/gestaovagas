package br.com.joaopmazzo.gestao_vagas.security;

import br.com.joaopmazzo.gestao_vagas.providers.JWTCandidateProvider;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SecurityCandidateFilter extends OncePerRequestFilter {

    private final JWTCandidateProvider jwtCandidateProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        SecurityContextHolder.getContext().setAuthentication(null); // serve para limpar sujeiras
        String header = request.getHeader("Authorization");

        if (request.getRequestURI().startsWith("/candidate")) {
            if (Objects.nonNull(header)) {
                DecodedJWT subjectToken = jwtCandidateProvider.validateToken(header);

                if (Objects.isNull(subjectToken)) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                request.setAttribute("candidate_id", subjectToken.getSubject());

                // Serve para o spring sempre validar se o usuario está autenticado e se possui as roles
                // necessárias
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        subjectToken, null, Collections.emptyList()
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }
}
