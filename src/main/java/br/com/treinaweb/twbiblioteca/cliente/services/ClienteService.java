package br.com.treinaweb.twbiblioteca.cliente.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.treinaweb.twbiblioteca.cliente.dtos.request.ClienteRequest;
import br.com.treinaweb.twbiblioteca.cliente.dtos.response.ClienteResponse;
import br.com.treinaweb.twbiblioteca.cliente.exceptions.ClienteNaoEncontradoException;
import br.com.treinaweb.twbiblioteca.cliente.mappers.ClienteMapper;
import br.com.treinaweb.twbiblioteca.cliente.models.Cliente;
import br.com.treinaweb.twbiblioteca.cliente.repositories.ClienteRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClienteService {

    private ClienteRepository repository;

    private ClienteMapper mapper;

    public List<ClienteResponse> buscarTodos() {
        return repository.findAll()
            .stream()
            .map(mapper::toResponse)
            .collect(Collectors.toList());
    }

    public ClienteResponse cadastrar(ClienteRequest request) {
        var clienteParaCadastrar = mapper.toModel(request);
        var clienteCadastrado = repository.save(clienteParaCadastrar);

        return mapper.toResponse(clienteCadastrado);
    }

    public ClienteResponse buscarPorId(Long id) {
        var clienteEncontrado = verificaSeExiste(id);

        return mapper.toResponse(clienteEncontrado);
    }

    public ClienteResponse atualizar(Long id, ClienteRequest request) {
        verificaSeExiste(id);

        var clienteParaAtualizar = mapper.toModel(request);
        clienteParaAtualizar.setId(id);
        var clienteAtualizado = repository.save(clienteParaAtualizar);

        return mapper.toResponse(clienteAtualizado);
    }

    public void excluirPorId(Long id) {
        var clienteParaExcluir = verificaSeExiste(id);

        repository.delete(clienteParaExcluir);
    }

    public Cliente verificaSeExiste(Long id) {
        var mensagem = String.format("Cliente com ID %d não encontrado", id);

        return repository.findById(id)
            .orElseThrow(() -> new ClienteNaoEncontradoException(mensagem));
    }

}
