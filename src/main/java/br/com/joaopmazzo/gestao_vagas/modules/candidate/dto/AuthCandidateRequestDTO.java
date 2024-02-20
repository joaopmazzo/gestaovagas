package br.com.joaopmazzo.gestao_vagas.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthCandidateRequestDTO(
        @Schema(example = "desenvolvedorjunior", requiredMode = Schema.RequiredMode.REQUIRED) String username,
        @Schema(example = "123@Exemple", requiredMode = Schema.RequiredMode.REQUIRED) String password) {

}
