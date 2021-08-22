package br.com.treinaweb.twbiblioteca.obras.builders;

import br.com.treinaweb.twbiblioteca.autor.builders.AutorBuilder;
import br.com.treinaweb.twbiblioteca.obra.dtos.response.ObraResponse;
import br.com.treinaweb.twbiblioteca.obra.enums.Tipo;

public class ObraResponseBuilder {

    private ObraResponse obraResponse;

    public static ObraResponseBuilder builder() {
        var builder = new ObraResponseBuilder();

        var id = 1L;
        var nome = "O Senhor dos Anéis: A Sociedade do Anel";
        var qtdPaginas = 576;
        var tipo = Tipo.LIVRO;
        var autorId = AutorBuilder.builder().build().getId();
        var obraResponse = new ObraResponse(id, nome, qtdPaginas, tipo, autorId);

        builder.obraResponse = obraResponse;

        return builder;
    }

    public ObraResponse build() {
        return obraResponse;
    }

}
