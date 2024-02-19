package br.com.joaopmazzo.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateJobDTO {

    @Schema(example = "Vaga para desenvolvedor Java Junior", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @Schema(example = "VF, VR, Plano de Saúde", requiredMode = Schema.RequiredMode.REQUIRED)
    private String benefits;

    @Schema(example = "Júnior", requiredMode = Schema.RequiredMode.REQUIRED)
    private String level;

}