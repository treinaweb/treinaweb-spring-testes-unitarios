package br.com.treinaweb.twbiblioteca.obra.dtos.response;

import br.com.treinaweb.twbiblioteca.obra.enums.Tipo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObraResponse {

    private Long id;

    private String nome;

    private Integer qtdPaginas;

    private Tipo tipo;

    private Long autorId;

}
