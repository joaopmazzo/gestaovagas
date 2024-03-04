package br.com.joaopmazzo.gestao_vagas.modules.company.controllers;

import br.com.joaopmazzo.gestao_vagas.exceptions.CompanyNotFoundException;
import br.com.joaopmazzo.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.joaopmazzo.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.joaopmazzo.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.joaopmazzo.gestao_vagas.modules.utils.TestUtils;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateJobControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private CompanyRepository companyRepository;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    @DisplayName("Should be able to create a new job")
    public void should_be_able_to_create_a_new_job() throws Exception {
        CompanyEntity companyEntity = CompanyEntity.builder()
                .description("COMPANY_DESCRIPTION")
                .email("EMAIL@COMPANY.COM.BR")
                .password("123456789abc")
                .username("COMPANY_USERNAME")
                .name("COMPANY_NAME")
                .build();
        companyEntity = companyRepository.saveAndFlush(companyEntity);

        CreateJobDTO createJobDTO = CreateJobDTO.builder()
                .description("DESCRIPTION_TEST")
                .benefits("BENEFITS_TEST")
                .level("LEVEL_TEST")
                .build();

        mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/company/job/")
                                .header("Authorization", TestUtils.generateToken(companyEntity.getId(), "JAVAGAS_@123#COMPANY"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.objectToJSON(createJobDTO))
                )
                .andExpect(
                        MockMvcResultMatchers.status().isOk()
                );
    }

    @Test
    @DisplayName("Should not be able to create a new job if company not found")
    public void should_not_be_able_to_create_a_new_job_if_company_not_found() throws Exception {
        CreateJobDTO createJobDTO = CreateJobDTO.builder()
                .description("DESCRIPTION_TEST")
                .benefits("BENEFITS_TEST")
                .level("LEVEL_TEST")
                .build();

        mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/company/job/")
                                .header("Authorization", TestUtils.generateToken(UUID.randomUUID(), "JAVAGAS_@123#COMPANY"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.objectToJSON(createJobDTO))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
