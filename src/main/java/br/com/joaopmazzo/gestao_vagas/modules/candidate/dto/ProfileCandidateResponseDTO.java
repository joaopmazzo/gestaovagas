package br.com.joaopmazzo.gestao_vagas.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCandidateResponseDTO {

    private UUID id;

    @Schema(example = "desenvolvedorjunior")
    private String username;

    @Schema(example = "Desenvolvedor da Silva Junior")
    private String name;

    @Schema(example = "desenvolvedor@email.com.br")
    private String email;

    @Schema(example = "Desenvolvedor Fullstack")
    private String description;

}
