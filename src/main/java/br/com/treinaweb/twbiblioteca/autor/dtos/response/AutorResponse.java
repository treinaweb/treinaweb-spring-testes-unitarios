package br.com.treinaweb.twbiblioteca.autor.dtos.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutorResponse {

    private Long id;

    private String nome;

    private LocalDate dataNascimento;

    private LocalDate dataFalecimento;

}
