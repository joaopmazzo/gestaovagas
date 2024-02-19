package br.com.joaopmazzo.gestao_vagas.modules.candidate;

import java.time.LocalDateTime;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "candidate")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CandidateEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Schema(example = "Desenvolvedor da Silva Junior", requiredMode = Schema.RequiredMode.REQUIRED, description = "Nome do candidato")
    private String name;

    @NotBlank(message = "Não pode ser vazio")
    @Pattern(regexp = "\\S+", message = "Não deve conter espaço")
    @Schema(example = "desenvolvedorjunior", requiredMode = Schema.RequiredMode.REQUIRED, description = "Username do candidato")
    private String username;
    
    @Email(message = "Deve conter um e-mail válido")
    @Schema(example = "desenvolvedor@email.com.br", requiredMode = Schema.RequiredMode.REQUIRED, description = "Email do candidato")
    private String email;

    @Length(min = 10, max = 100, message = "A senha deve conter ente 10 a 100 caracteres")
    @Schema(example = "123@Exemple", minLength = 10, maxLength = 100, requiredMode = Schema.RequiredMode.REQUIRED, description = "Senha do candidato")
    private String password;

    @Schema(example = "Desenvolvedor Fullstack", requiredMode = Schema.RequiredMode.REQUIRED, description = "Breve descrição do candidato")
    private String description;
    private String curriculum;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
