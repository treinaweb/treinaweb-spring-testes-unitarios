package br.com.treinaweb.twbiblioteca.cliente.dtos.request;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequest {

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 255)
    private String nome;

    @DateTimeFormat(iso = ISO.DATE)
    @NotNull
    @Past
    private LocalDate dataNascimento;

    @NotNull
    @NotEmpty
    @Size(min = 11, max = 11)
    @CPF
    private String cpf;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 7)
    @Pattern(regexp = "RUIM|REGULAR|BOA")
    private String reputacao;

}
