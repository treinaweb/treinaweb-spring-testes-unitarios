package br.com.treinaweb.twbiblioteca.cliente.mappers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.treinaweb.twbiblioteca.cliente.builders.ClienteBuilder;
import br.com.treinaweb.twbiblioteca.cliente.builders.ClienteResponseBuilder;
import br.com.treinaweb.twbiblioteca.cliente.builders.ClienteRequestBuilder;
import br.com.treinaweb.twbiblioteca.cliente.dtos.request.ClienteRequest;
import br.com.treinaweb.twbiblioteca.cliente.models.Cliente;

public class ClienteMapperTest {

    private ClienteMapper mapper;

    @BeforeEach
    private void setUp() {
        mapper = new ClienteMapper();
    }

    @Test
    void quandoToModelEhChamadoComClienteRequestNuloDeveLancarExceptionIllegalArgumentException() {
        ClienteRequest request = null;
        var mensagemEsperada = "Parâmetro request não pode ser nulo";

        var exception = assertThrows(IllegalArgumentException.class, () -> mapper.toModel(request));
        assertEquals(mensagemEsperada, exception.getMessage());
    }

    @Test
    void quandoToModelEhChamadoComClienteRequestValidoDeveRetornarUmCliente() {
        var request = ClienteRequestBuilder.builder().build();
        var clienteEsperado = ClienteBuilder.builder().build();

        var clienteRetornado = mapper.toModel(request);

        assertNull(clienteRetornado.getId());
        assertEquals(clienteEsperado.getNome(), clienteRetornado.getNome());
        assertEquals(clienteEsperado.getCpf(), clienteRetornado.getCpf());
        assertEquals(clienteEsperado.getCpf(), clienteRetornado.getCpf());
        assertEquals(clienteEsperado.getDataNascimento(), clienteRetornado.getDataNascimento());
        assertEquals(clienteEsperado.getReputacao(), clienteRetornado.getReputacao());
    }

    @Test
    void quandoToResponseEhChamadoComClienteNuloDeveLancarexceptionIllegalArgumentException() {
        Cliente model = null;
        var mensagemEsperada = "Parâmetro model não pode ser nulo";

        var exception = assertThrows(IllegalArgumentException.class, () -> mapper.toResponse(model));
        assertEquals(mensagemEsperada, exception.getMessage());
    }

    @Test
    void quandoToResponseEhChamadoComClienteValidoDeveRetornarClienteResponse() {
        var model = ClienteBuilder.builder().build();
        var clienteResponseEsperado = ClienteResponseBuilder.builder().build();

        var clienteResponseRetornado = mapper.toResponse(model);

        assertEquals(clienteResponseEsperado.getId(), clienteResponseRetornado.getId());
        assertEquals(clienteResponseEsperado.getNome(), clienteResponseRetornado.getNome());
        assertEquals(clienteResponseEsperado.getCpf(), clienteResponseRetornado.getCpf());
        assertEquals(clienteResponseEsperado.getDataNascimento(), clienteResponseRetornado.getDataNascimento());
        assertEquals(clienteResponseEsperado.getReputacao(), clienteResponseRetornado.getReputacao());
    }

}
