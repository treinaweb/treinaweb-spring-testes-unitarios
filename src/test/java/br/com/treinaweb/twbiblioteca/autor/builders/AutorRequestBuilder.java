package br.com.treinaweb.twbiblioteca.autor.builders;

import java.time.LocalDate;

import br.com.treinaweb.twbiblioteca.autor.dtos.request.AutorRequest;

public class AutorRequestBuilder {

    private AutorRequest autorRequest;

    public static AutorRequestBuilder builder() {
        var builder = new AutorRequestBuilder();

        var nome = "J. R. R. Tolkien";
        var dataNascimento = LocalDate.of(1892, 1, 3);
        var dataFalecimento = LocalDate.of(1973, 9, 2);
        var autorRequest = new AutorRequest(nome, dataNascimento, dataFalecimento);

        builder.autorRequest = autorRequest;

        return builder;
    }

    public AutorRequestBuilder nome(String nome) {
        autorRequest.setNome(nome);

        return this;
    }

    public AutorRequest build() {
        return autorRequest;
    }

}
