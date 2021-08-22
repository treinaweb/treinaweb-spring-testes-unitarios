package br.com.treinaweb.twbiblioteca.cliente.builders;

import java.time.LocalDate;

import br.com.treinaweb.twbiblioteca.cliente.dtos.request.ClienteRequest;

public class ClienteRequestBuilder {

    private ClienteRequest clienteRequest;

    public static ClienteRequestBuilder builder() {
        var builder = new ClienteRequestBuilder();

        var nome = "Cleyson Lima";
        var dataNascimento = LocalDate.of(1996, 1, 1);
        var cpf = "35599201082";
        var reputacao = "REGULAR";
        var clienteRequest = new ClienteRequest(nome, dataNascimento, cpf, reputacao);

        builder.clienteRequest = clienteRequest;

        return builder;
    }

    public ClienteRequest build() {
        return clienteRequest;
    }

}
