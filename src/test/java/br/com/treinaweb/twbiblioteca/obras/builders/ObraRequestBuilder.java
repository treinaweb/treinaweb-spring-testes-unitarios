package br.com.treinaweb.twbiblioteca.obras.builders;

import br.com.treinaweb.twbiblioteca.autor.builders.AutorBuilder;
import br.com.treinaweb.twbiblioteca.obra.dtos.request.ObraRequest;

public class ObraRequestBuilder {

    private ObraRequest obraRequest;

    public static ObraRequestBuilder builder() {
        var builder = new ObraRequestBuilder();

        var nome = "O Senhor dos Anéis: A Sociedade do Anel";
        var qtdPaginas = 576;
        var tipo = "LIVRO";
        var autorId = AutorBuilder.builder().build().getId();
        var obraRequest = new ObraRequest(nome, qtdPaginas, tipo, autorId);

        builder.obraRequest = obraRequest;

        return builder;
    }

    public ObraRequest build() {
        return obraRequest;
    }

}
