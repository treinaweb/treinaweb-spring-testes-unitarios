package br.com.treinaweb.twbiblioteca.cliente.mappers;

import org.springframework.stereotype.Component;

import br.com.treinaweb.twbiblioteca.cliente.dtos.request.ClienteRequest;
import br.com.treinaweb.twbiblioteca.cliente.dtos.response.ClienteResponse;
import br.com.treinaweb.twbiblioteca.cliente.enums.Reputacao;
import br.com.treinaweb.twbiblioteca.cliente.models.Cliente;

@Component
public class ClienteMapper {

    public Cliente toModel(ClienteRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Parâmetro request não pode ser nulo");
        }

        var model = new Cliente();

        model.setNome(request.getNome());
        model.setDataNascimento(request.getDataNascimento());
        model.setCpf(request.getCpf());
        var reputacao = Reputacao.valueOf(request.getReputacao());
        model.setReputacao(reputacao);

        return model;
    }

    public ClienteResponse toResponse(Cliente model) {
        if (model == null) {
            throw new IllegalArgumentException("Parâmetro model não pode ser nulo");
        }

        var response = new ClienteResponse();

        response.setId(model.getId());
        response.setNome(model.getNome());
        response.setDataNascimento(model.getDataNascimento());
        response.setCpf(model.getCpf());
        response.setReputacao(model.getReputacao());

        return response;
    }

}
