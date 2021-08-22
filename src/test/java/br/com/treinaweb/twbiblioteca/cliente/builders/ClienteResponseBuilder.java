package br.com.treinaweb.twbiblioteca.cliente.builders;

import java.time.LocalDate;

import br.com.treinaweb.twbiblioteca.cliente.dtos.response.ClienteResponse;
import br.com.treinaweb.twbiblioteca.cliente.enums.Reputacao;

public class ClienteResponseBuilder {

    private ClienteResponse clienteResponse;

    public static ClienteResponseBuilder builder() {
        var builder = new ClienteResponseBuilder();

        var id = 1L;
        var nome = "Cleyson Lima";
        var dataNascimento = LocalDate.of(1996, 1, 1);
        var cpf = "35599201082";
        var reputacao = Reputacao.REGULAR;
        var clienteResponse = new ClienteResponse(id, nome, dataNascimento, cpf, reputacao);

        builder.clienteResponse = clienteResponse;

        return builder;
    }

    public ClienteResponse build() {
        return clienteResponse;
    }

}
