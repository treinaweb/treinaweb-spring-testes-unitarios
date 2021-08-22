package br.com.treinaweb.twbiblioteca.autor.builders;

import java.time.LocalDate;

import br.com.treinaweb.twbiblioteca.autor.models.Autor;

public class AutorBuilder {

    private Autor autor;

    public static AutorBuilder builder() {
        var builder = new AutorBuilder();

        var id = 1L;
        var nome = "J. R. R. Tolkien";
        var dataNascimento = LocalDate.of(1892, 1, 3);
        var dataFalecimento = LocalDate.of(1973, 9, 2);
        var autor = new Autor(id, nome, dataNascimento, dataFalecimento);

        builder.autor = autor;

        return builder;
    }

    public Autor build() {
        return autor;
    }

}
