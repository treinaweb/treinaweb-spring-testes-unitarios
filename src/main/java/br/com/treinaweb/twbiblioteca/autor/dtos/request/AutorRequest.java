package br.com.treinaweb.twbiblioteca.autor.dtos.request;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutorRequest {

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 255)
    private String nome;

    @DateTimeFormat(iso = ISO.DATE)
    @NotNull
    private LocalDate dataNascimento;

    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate dataFalecimento;

}
