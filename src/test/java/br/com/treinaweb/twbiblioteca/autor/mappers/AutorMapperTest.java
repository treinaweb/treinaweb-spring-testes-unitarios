package br.com.treinaweb.twbiblioteca.autor.mappers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.treinaweb.twbiblioteca.autor.builders.AutorBuilder;
import br.com.treinaweb.twbiblioteca.autor.builders.AutorRequestBuilder;
import br.com.treinaweb.twbiblioteca.autor.builders.AutorResponseBuilder;
import br.com.treinaweb.twbiblioteca.autor.dtos.request.AutorRequest;
import br.com.treinaweb.twbiblioteca.autor.models.Autor;

public class AutorMapperTest {

    private AutorMapper mapper;

    @BeforeEach
    private void setUp() {
        mapper = new AutorMapper();
    }

    @Test
    void quandoToModelEhChamadoComAutorRequestNuloDeveLancarExcecaoIllegalArgumentException() {
        AutorRequest request = null;
        var mensagemEsperada = "Parâmetro request não pode ser nulo";

        var exception = assertThrows(IllegalArgumentException.class, () -> mapper.toModel(request));
        assertEquals(mensagemEsperada, exception.getMessage());
    }

    @Test
    void quandoToModelEhChamadoComAutorRequestValidoDeveRetornarUmAutor() {
        var request = AutorRequestBuilder.builder().build();
        var autorEsperado = AutorBuilder.builder().build();

        var autorRetornado = mapper.toModel(request);

        assertNull(autorRetornado.getId());
        assertEquals(autorEsperado.getNome(), autorRetornado.getNome());
        assertEquals(autorEsperado.getDataNascimento(), autorRetornado.getDataNascimento());
        assertEquals(autorEsperado.getDataFalecimento(), autorRetornado.getDataFalecimento());
    }

    @Test
    void quandoToResponseEhChamadoComAutorNuloDeveLancarExcecaoIllegalArgumentException() {
        Autor model = null;
        var mensagemEsperada = "Parâmetro model não pode ser nulo";

        var exception = assertThrows(IllegalArgumentException.class, () -> mapper.toResponse(model));
        assertEquals(mensagemEsperada, exception.getMessage());
    }

    @Test
    void quandoToResponseEhChamadoComAutorValidoDeveRetornarUmAutorResponse() {
        var model = AutorBuilder.builder().build();
        var autorResponseEsperado = AutorResponseBuilder.builder().build();

        var autorResponseRetornado = mapper.toResponse(model);

        assertEquals(autorResponseEsperado.getId(), autorResponseRetornado.getId());
        assertEquals(autorResponseEsperado.getNome(), autorResponseRetornado.getNome());
        assertEquals(autorResponseEsperado.getDataNascimento(), autorResponseRetornado.getDataNascimento());
        assertEquals(autorResponseEsperado.getDataFalecimento(), autorResponseRetornado.getDataFalecimento());
    }

}
