package br.com.treinaweb.twbiblioteca.emprestimo.builders;

import java.util.Set;

import br.com.treinaweb.twbiblioteca.cliente.builders.ClienteBuilder;
import br.com.treinaweb.twbiblioteca.emprestimo.dtos.request.EmprestimoRequest;
import br.com.treinaweb.twbiblioteca.obras.builders.ObraBuilder;

public class EmprestimoRequestBuilder {

    public EmprestimoRequest emprestimoRequest;

    public static EmprestimoRequestBuilder builder() {
        var builder = new EmprestimoRequestBuilder();

        var clienteId = ClienteBuilder.builder().build().getId();
        var obras = Set.of(ObraBuilder.builder().build().getId());
        var emprestimoRequest = new EmprestimoRequest(clienteId, obras);

        builder.emprestimoRequest = emprestimoRequest;

        return builder;
    }

    public EmprestimoRequest build() {
        return emprestimoRequest;
    }

}
