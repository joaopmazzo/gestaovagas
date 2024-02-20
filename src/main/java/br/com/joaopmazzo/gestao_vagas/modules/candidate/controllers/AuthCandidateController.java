package br.com.joaopmazzo.gestao_vagas.modules.candidate.controllers;

import br.com.joaopmazzo.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.joaopmazzo.gestao_vagas.modules.candidate.usecases.AuthCandidateUseCase;
import br.com.joaopmazzo.gestao_vagas.security.AuthResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
        name = "Candidato - Autenticação",
        description = "Autenticação do candidato"
)
public class AuthCandidateController {

    private final AuthCandidateUseCase authCandidateUseCase;

    @PostMapping("/auth")
    @Operation(
            summary = "Autenticação de um candidato na plataforma",
            description = "Essa função é responsável por autenticar e logar o candidato na plataforma"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = AuthResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Username/password incorrect")
    })
    public ResponseEntity<Object> auth(@RequestBody AuthCandidateRequestDTO authCandidateRequestDTO) {
        try {
            AuthResponseDTO token = authCandidateUseCase.execute(authCandidateRequestDTO);
            return ResponseEntity.ok().body(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
