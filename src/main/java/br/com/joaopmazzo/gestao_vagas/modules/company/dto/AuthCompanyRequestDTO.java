package br.com.joaopmazzo.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthCompanyRequestDTO(
        @Schema(example = "joaomazzotecnologia", requiredMode = Schema.RequiredMode.REQUIRED) String username,
        @Schema(example = "123@Exemple", requiredMode = Schema.RequiredMode.REQUIRED) String password) {

}