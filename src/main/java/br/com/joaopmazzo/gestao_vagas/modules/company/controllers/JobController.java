package br.com.joaopmazzo.gestao_vagas.modules.company.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.joaopmazzo.gestao_vagas.modules.company.entities.JobEntity;
import br.com.joaopmazzo.gestao_vagas.modules.company.usecases.CreateJobUseCase;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/job")
@RequiredArgsConstructor
public class JobController {

    private final CreateJobUseCase createJobUseCase;

    @PostMapping("/")
    public JobEntity create(@Valid @RequestBody JobEntity jobEntity) {
        return createJobUseCase.execute(jobEntity);
    }

}
