package br.com.treinaweb.twbiblioteca.cliente.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.treinaweb.twbiblioteca.cliente.builders.ClienteBuilder;
import br.com.treinaweb.twbiblioteca.cliente.builders.ClienteResponseBuilder;
import br.com.treinaweb.twbiblioteca.cliente.builders.ClienteRequestBuilder;
import br.com.treinaweb.twbiblioteca.cliente.exceptions.ClienteNaoEncontradoException;
import br.com.treinaweb.twbiblioteca.cliente.mappers.ClienteMapper;
import br.com.treinaweb.twbiblioteca.cliente.models.Cliente;
import br.com.treinaweb.twbiblioteca.cliente.repositories.ClienteRepository;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;

    @Mock
    private ClienteMapper mapper;

    @InjectMocks
    private ClienteService service;

    @Test
    void quandoBuscarTodoEhChamadoDeveRetornarUmaListaDeClienteResponse() {
        var model = ClienteBuilder.builder().build();
        var clienteResponseEsperado = ClienteResponseBuilder.builder().build();
        var modelList = List.of(model);
        var clienteResponseListEsperado = List.of(clienteResponseEsperado);

        when(repository.findAll()).thenReturn(modelList);
        when(mapper.toResponse(model)).thenReturn(clienteResponseEsperado);

        var clienteResponseListRetornado = service.buscarTodos();

        assertEquals(clienteResponseListEsperado.size(), clienteResponseListRetornado.size());
        assertEquals(clienteResponseListEsperado.get(0), clienteResponseListRetornado.get(0));
    }

    @Test
    void quandoCadastrarEhChamadoComClienteRequestValidoDeveRetornarUmClienteResponse() {
        var model = ClienteBuilder.builder().build();
        var request = ClienteRequestBuilder.builder().build();
        var clienteResponseEsperado = ClienteResponseBuilder.builder().build();

        when(mapper.toModel(request)).thenReturn(model);
        when(repository.save(model)).thenReturn(model);
        when(mapper.toResponse(model)).thenReturn(clienteResponseEsperado);

        var clienteResponseRetornado = service.cadastrar(request);

        verify(repository, times(1)).save(model);
        assertEquals(clienteResponseEsperado.getId(), clienteResponseRetornado.getId());
        assertEquals(clienteResponseEsperado.getNome(), clienteResponseRetornado.getNome());
        assertEquals(clienteResponseEsperado.getCpf(), clienteResponseRetornado.getCpf());
        assertEquals(clienteResponseEsperado.getDataNascimento(), clienteResponseRetornado.getDataNascimento());
        assertEquals(clienteResponseEsperado.getReputacao(), clienteResponseRetornado.getReputacao());
    }

    @Test
    void quandoBuscarPorIdEhChamadoComIdInvalidoDeveLancarExceptionClienteNaoEncontradoException() {
        var id = 1L;
        var mensagemEsperada = String.format("Cliente com ID %d não encontrado", id);

        when(repository.findById(id)).thenReturn(Optional.<Cliente>empty());

        var exception = assertThrows(ClienteNaoEncontradoException.class, () -> service.buscarPorId(id));
        assertEquals(mensagemEsperada, exception.getMessage());
    }

    @Test
    void quandoBuscarPorIdEhChamadoComIdValidoDeveRetornarUmClienteResponse() {
        var model = ClienteBuilder.builder().build();
        var id  = model.getId();
        var clienteResponseEsperado = ClienteResponseBuilder.builder().build();

        when(repository.findById(id)).thenReturn(Optional.of(model));
        when(mapper.toResponse(model)).thenReturn(clienteResponseEsperado);

        var clienteResponseRetornado = service.buscarPorId(id);

        assertEquals(clienteResponseEsperado.getId(), clienteResponseRetornado.getId());
        assertEquals(clienteResponseEsperado.getNome(), clienteResponseRetornado.getNome());
        assertEquals(clienteResponseEsperado.getCpf(), clienteResponseRetornado.getCpf());
        assertEquals(clienteResponseEsperado.getDataNascimento(), clienteResponseRetornado.getDataNascimento());
        assertEquals(clienteResponseEsperado.getReputacao(), clienteResponseRetornado.getReputacao());
    }

    @Test
    void quandoAtualizarEhChamadoComIdInvalidoDeveLancarExceptionClienteNaoEncontradoException() {
        var id = 1L;
        var request = ClienteRequestBuilder.builder().build();
        var mensagemEsperada = String.format("Cliente com ID %d não encontrado", id);

        when(repository.findById(id)).thenReturn(Optional.<Cliente>empty());

        var exception = assertThrows(ClienteNaoEncontradoException.class, () -> service.atualizar(id, request));
        assertEquals(mensagemEsperada, exception.getMessage());
    }

    @Test
    void quandoAtualizarEhChamadoComDadosValidosDeveRetornarUmClienteResponse() {
        var model = ClienteBuilder.builder().build();
        var id = model.getId();
        var request = ClienteRequestBuilder.builder().build();
        var clienteResponseEsperado = ClienteResponseBuilder.builder().build();

        when(repository.findById(id)).thenReturn(Optional.of(model));
        when(mapper.toModel(request)).thenReturn(model);
        when(repository.save(model)).thenReturn(model);
        when(mapper.toResponse(model)).thenReturn(clienteResponseEsperado);

        var clienteResponseRetornado = service.atualizar(id, request);

        verify(repository, times(1)).save(model);
        assertEquals(clienteResponseEsperado.getId(), clienteResponseRetornado.getId());
        assertEquals(clienteResponseEsperado.getNome(), clienteResponseRetornado.getNome());
        assertEquals(clienteResponseEsperado.getCpf(), clienteResponseRetornado.getCpf());
        assertEquals(clienteResponseEsperado.getDataNascimento(), clienteResponseRetornado.getDataNascimento());
        assertEquals(clienteResponseEsperado.getReputacao(), clienteResponseRetornado.getReputacao());
    }

    @Test
    void quandoExcluirPorIdEhChamadoComIdInvalidoDeveLancarExceptionClienteNaoEncontradoException() {
        var id = 1L;
        var mensagemEsperada = String.format("Cliente com ID %d não encontrado", id);

        var exception = assertThrows(ClienteNaoEncontradoException.class, () -> service.excluirPorId(id));
        assertEquals(mensagemEsperada, exception.getMessage());
    }

    @Test
    void quandoExcluirPorIdEhChamadoComIdValidoDeveChamarDeleteDeRepository() {
        var model = ClienteBuilder.builder().build();
        var id = model.getId();

        when(repository.findById(id)).thenReturn(Optional.of(model));

        service.excluirPorId(id);

        verify(repository, times(1)).delete(model);
    }

}
