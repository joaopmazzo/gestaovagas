package br.com.joaopmazzo.gestao_vagas.modules.company.controllers;

import br.com.joaopmazzo.gestao_vagas.modules.company.dto.CreateJobDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.joaopmazzo.gestao_vagas.modules.company.entities.JobEntity;
import br.com.joaopmazzo.gestao_vagas.modules.company.usecases.CreateJobUseCase;
import jakarta.validation.Valid;

import java.util.UUID;

@RestController
@RequestMapping("/job")
@RequiredArgsConstructor
public class JobController {

    private final CreateJobUseCase createJobUseCase;

    @PostMapping("/")
    public JobEntity create(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request) {
        Object companyId = request.getAttribute("company_id"); // recuperando o atributo setado no login

        JobEntity jobEntity = JobEntity.builder()
                .benefits(createJobDTO.getBenefits())
                .description(createJobDTO.getDescription())
                .level(createJobDTO.getLevel())
                .companyId(UUID.fromString(companyId.toString())) // setando na entidade que queremos salvar
                .build();

        return createJobUseCase.execute(jobEntity);
    }

}
