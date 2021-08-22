package br.com.treinaweb.twbiblioteca.obras.mappers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.treinaweb.twbiblioteca.obra.dtos.request.ObraRequest;
import br.com.treinaweb.twbiblioteca.obra.mappers.ObraMapper;
import br.com.treinaweb.twbiblioteca.obra.models.Obra;
import br.com.treinaweb.twbiblioteca.obras.builders.ObraBuilder;
import br.com.treinaweb.twbiblioteca.obras.builders.ObraRequestBuilder;
import br.com.treinaweb.twbiblioteca.obras.builders.ObraResponseBuilder;

public class ObraMapperTest {

    private ObraMapper mapper;

    @BeforeEach
    private void setUp() {
        mapper = new ObraMapper();
    }

    @Test
    void quandoToModelEhChamadoComObraRequestNuloDeveLancarExceptionIllegalArgumentException() {
        ObraRequest request = null;
        var mensagemEsperada = "Parâmetro request não pode ser nulo";

        var exception = assertThrows(IllegalArgumentException.class, () -> mapper.toModel(request));
        assertEquals(mensagemEsperada, exception.getMessage());
    }

    @Test
    void quandoToModelEhChamadoComObraRequestValidoDeveRetornarUmaObra() {
        var request = ObraRequestBuilder.builder().build();
        var obraEsperada = ObraBuilder.builder().build();

        var obraRetornada = mapper.toModel(request);

        assertNull(obraRetornada.getId());
        assertEquals(obraEsperada.getNome(), obraRetornada.getNome());
        assertEquals(obraEsperada.getQtdPaginas(), obraRetornada.getQtdPaginas());
        assertEquals(obraEsperada.getTipo(), obraRetornada.getTipo());
        assertNull(obraRetornada.getAutor());
    }

    @Test
    void quandoToResponseEhChamadoComObraNuloDeveLancarExceptionIllegalArgumentException() {
        Obra model = null;
        var mensagemEsperada = "Parâmetro model não pode ser nulo";

        var exception = assertThrows(IllegalArgumentException.class, () -> mapper.toResponse(model));
        assertEquals(mensagemEsperada, exception.getMessage());
    }

    @Test
    void quandoToResponseEhChamadoComObraValidoDeveRetornarUmaObraResponse() {
        var model = ObraBuilder.builder().build();
        var obraResponseEsperada = ObraResponseBuilder.builder().build();

        var obraResponseRetornada = mapper.toResponse(model);

        assertEquals(obraResponseEsperada.getId(), obraResponseRetornada.getId());
        assertEquals(obraResponseEsperada.getNome(), obraResponseRetornada.getNome());
        assertEquals(obraResponseEsperada.getQtdPaginas(), obraResponseRetornada.getQtdPaginas());
        assertEquals(obraResponseEsperada.getTipo(), obraResponseRetornada.getTipo());
        assertEquals(obraResponseEsperada.getAutorId(), obraResponseRetornada.getAutorId());
    }

}
