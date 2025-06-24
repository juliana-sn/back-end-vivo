package br.purpletech.vivo.dtos.user;

import br.purpletech.vivo.models.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserToCreateDTO(
        @NotBlank(message = "Nome é obrigatório")
        String name,

        @NotBlank(message = "Sobrenome é obrigatório")
        String lastName,

        @Email(message = "E-mail inválido")
        @NotBlank(message = "E-mail é obrigatório")
        String email,

        @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
        String password,

        @NotBlank(message = "Cargo é obrigatório")
        String position,

        @NotBlank(message = "Telefone é obrigatório")
        String telephone,

        @NotNull(message = "Função (role) é obrigatória")
        Role role
) { }
