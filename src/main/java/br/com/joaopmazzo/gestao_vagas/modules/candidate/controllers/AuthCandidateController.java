package br.com.joaopmazzo.gestao_vagas.modules.candidate.controllers;

import br.com.joaopmazzo.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.joaopmazzo.gestao_vagas.modules.candidate.usecases.AuthCandidateUseCase;
import br.com.joaopmazzo.gestao_vagas.security.AuthResponseDTO;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidate")
@RequiredArgsConstructor
public class AuthCandidateController {

    private final AuthCandidateUseCase authCandidateUseCase;

    @PostMapping("/auth")
    public ResponseEntity<Object> auth(@RequestBody AuthCandidateRequestDTO authCandidateRequestDTO) {
        try {
            AuthResponseDTO token = authCandidateUseCase.execute(authCandidateRequestDTO);
            return ResponseEntity.ok().body(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
