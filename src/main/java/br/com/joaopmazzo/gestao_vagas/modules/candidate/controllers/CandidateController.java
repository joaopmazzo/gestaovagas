package br.com.joaopmazzo.gestao_vagas.modules.candidate.controllers;

import br.com.joaopmazzo.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import br.com.joaopmazzo.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.joaopmazzo.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.joaopmazzo.gestao_vagas.modules.candidate.usecases.ApplyJobCandidateUseCase;
import br.com.joaopmazzo.gestao_vagas.modules.candidate.usecases.CreateCandidateUseCase;
import br.com.joaopmazzo.gestao_vagas.modules.candidate.usecases.ListAllJobsByFilterUseCase;
import br.com.joaopmazzo.gestao_vagas.modules.candidate.usecases.ProfileCandidateUseCase;
import br.com.joaopmazzo.gestao_vagas.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/candidate")
@RequiredArgsConstructor
@Tag(
        name = "Candidato",
        description = "Informações do candidato"
)
public class CandidateController {

    private final CreateCandidateUseCase createCandidateUseCase;
    private final ProfileCandidateUseCase profileCandidateUseCase;
    private final ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;
    private final ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @PostMapping("/")
    @Operation(
            summary = "Criação de um perfil de um candidato novo",
            description = "Essa função é responsável por criar o perfil de um candidato novo"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = CandidateEntity.class))
            }),
            @ApiResponse(responseCode = "400", description = "User already exists")
    })
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
        try {
            var result = createCandidateUseCase.execute(candidateEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(
            summary = "Perfil do candidato",
            description = "Essa função é responsável buscar as informações do perfil do candidato"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "User not found")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> get(HttpServletRequest request) {
        try {
            Object candidateId = request.getAttribute("candidate_id");
            var profile = profileCandidateUseCase.execute(UUID.fromString(candidateId.toString()));
            return ResponseEntity.ok().body(profile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(
            summary = "Listagem de vagas disponíveis para o candidato",
            description = "Essa função é responsável por listar todas as vagas disponíveis, baseadas no filtro (description)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(
                            schema = @Schema(implementation = JobEntity.class)
                    ))
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> findJobByDescription(@RequestParam String description) {
        try {
            return ResponseEntity.ok().body(listAllJobsByFilterUseCase.execute(description));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/job/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(
            summary = "Aplica o candidato para uma vaga",
            description = "Essa função é responsável por aplicar o candidato para uma vaga"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ApplyJobEntity.class))
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> applyJob(HttpServletRequest request, @RequestBody UUID idJob) {
        try {
            Object candidateId = request.getAttribute("candidate_id");
            return ResponseEntity.ok().body(applyJobCandidateUseCase.execute(UUID.fromString(candidateId.toString()), idJob));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
