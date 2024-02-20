package br.com.joaopmazzo.gestao_vagas.modules.company.entities;

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
import lombok.Data;

@Entity(name = "company")
@Data
public class CompanyEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Schema(example = "Joao Mazzo Tecnologia", requiredMode = Schema.RequiredMode.REQUIRED, description = "Nome da empresa")
    private String name;
    
    @NotBlank(message = "Não pode ser vazio")
    @Pattern(regexp = "\\S+", message = "Não deve conter espaço")
    @Schema(example = "joaomazzotecnologia", requiredMode = Schema.RequiredMode.REQUIRED, description = "Nome da empresa")
    private String username;
    
    @Email(message = "Deve conter um e-mail válido")
    @Schema(example = "Joao Mazzo Tecnologia", requiredMode = Schema.RequiredMode.REQUIRED, description = "Nome da empresa")
    private String email;

    @Length(min = 10, max = 100, message = "A senha deve conter ente 10 a 100 caracteres")
    @Schema(example = "123@Exemple", minLength = 10, maxLength = 100, requiredMode = Schema.RequiredMode.REQUIRED, description = "Senha da empresa")
    private String password;

    @Schema(example = "www.joaomazzotecnologia.com.br", minLength = 10, maxLength = 100, requiredMode = Schema.RequiredMode.REQUIRED, description = "Senha da empresa")
    private String website;

    @Schema(example = "Empresa de tecnologia", minLength = 10, maxLength = 100, requiredMode = Schema.RequiredMode.REQUIRED, description = "Senha da empresa")
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
