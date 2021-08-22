package br.com.treinaweb.twbiblioteca.cliente.builders;

import java.time.LocalDate;

import br.com.treinaweb.twbiblioteca.cliente.enums.Reputacao;
import br.com.treinaweb.twbiblioteca.cliente.models.Cliente;

public class ClienteBuilder {

    private Cliente cliente;

    public static ClienteBuilder builder() {
        var builder = new ClienteBuilder();

        var id = 1L;
        var nome = "Cleyson Lima";
        var dataNascimento = LocalDate.of(1996, 1, 1);
        var cpf = "35599201082";
        var reputacao = Reputacao.REGULAR;
        var cliente = new Cliente(id, nome, dataNascimento, cpf, reputacao);

        builder.cliente = cliente;

        return builder;
    }

    public ClienteBuilder reputacao(Reputacao reputacao) {
        cliente.setReputacao(reputacao);

        return this;
    }

    public Cliente build() {
        return cliente;
    }

}
