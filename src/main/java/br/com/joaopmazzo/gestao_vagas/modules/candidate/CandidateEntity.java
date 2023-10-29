package br.com.joaopmazzo.gestao_vagas.modules.candidate;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CandidateEntity {
    
    private UUID id;
    private String name;

    @NotBlank(message = "Não pode ser vazio")
    @Pattern(regexp = "\\S+", message = "Não deve conter espaço")
    private String username;
    
    @Email(message = "Deve conter um e-mail válido")
    private String email;

    @Size(min = 6, max = 20, message = "Precisa ter um tamanho entre 6 e 20")
    private String password;
    private String description;
    private String curriculum;


}
