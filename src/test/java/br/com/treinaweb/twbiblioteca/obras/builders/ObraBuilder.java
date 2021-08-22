package br.com.treinaweb.twbiblioteca.obras.builders;

import br.com.treinaweb.twbiblioteca.autor.builders.AutorBuilder;
import br.com.treinaweb.twbiblioteca.autor.models.Autor;
import br.com.treinaweb.twbiblioteca.obra.enums.Tipo;
import br.com.treinaweb.twbiblioteca.obra.models.Obra;

public class ObraBuilder {

    private Obra obra;

    public static ObraBuilder builder() {
        var builder = new ObraBuilder();

        var id = 1L;
        var nome = "O Senhor dos Anéis: A Sociedade do Anel";
        var qtdPaginas = 576;
        var tipo = Tipo.LIVRO;
        var autor = AutorBuilder.builder().build();
        var obra = new Obra(id, nome, qtdPaginas, tipo, autor);

        builder.obra = obra;

        return builder;
    }

    public ObraBuilder id(Long id) {
        obra.setId(id);

        return this;
    }

    public ObraBuilder autor(Autor autor) {
        obra.setAutor(autor);

        return this;
    }

    public Obra build() {
        return obra;
    }

}
