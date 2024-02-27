package br.com.joaopmazzo.gestao_vagas.modules.company.controllers;

import br.com.joaopmazzo.gestao_vagas.modules.company.dto.CreateJobDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RequiredArgsConstructor
public class CreateJobControllerTest {

    private MockMvc mockMvc;

    private final WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void should_be_able_to_create_a_new_job() throws Exception {
        CreateJobDTO createJobDTO = CreateJobDTO.builder()
                .description("DESCRIPTION_TEST")
                .benefits("BENEFITS_TEST")
                .level("LEVEL_TEST")
                .build();

        ResultActions result = mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/company/job/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectToJSON(createJobDTO))
                )
                .andExpect(
                        MockMvcResultMatchers.status().isOk()
                );

        System.out.println(result);
    }

    private static String objectToJSON(Object object) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
