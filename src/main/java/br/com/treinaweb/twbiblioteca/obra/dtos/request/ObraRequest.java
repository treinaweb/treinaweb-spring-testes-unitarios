package br.com.treinaweb.twbiblioteca.obra.dtos.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObraRequest {

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 255)
    private String nome;

    @NotNull
    @Positive
    private Integer qtdPaginas;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 7)
    @Pattern(regexp = "LIVRO|REVISTA|HQ|ARTIGO")
    private String tipo;

    @NotNull
    @Positive
    private Long autorId;

}
