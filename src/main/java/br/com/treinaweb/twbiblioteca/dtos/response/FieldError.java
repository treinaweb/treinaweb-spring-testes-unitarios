package br.com.treinaweb.twbiblioteca.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldError {

    private String nome;

    private String causa;

}
