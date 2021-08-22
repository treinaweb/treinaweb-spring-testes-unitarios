package br.com.treinaweb.twbiblioteca.autor.builders;

import java.time.LocalDate;

import br.com.treinaweb.twbiblioteca.autor.dtos.response.AutorResponse;

public class AutorResponseBuilder {

    private AutorResponse autorResponse;

    public static AutorResponseBuilder builder() {
        var builder = new AutorResponseBuilder();

        var id = 1L;
        var nome = "J. R. R. Tolkien";
        var dataNascimento = LocalDate.of(1892, 1, 3);
        var dataFalecimento = LocalDate.of(1973, 9, 2);
        var autorResponse = new AutorResponse(id, nome, dataNascimento, dataFalecimento);

        builder.autorResponse = autorResponse;

        return builder;
    }

    public AutorResponse build() {
        return autorResponse;
    }

}
