package br.com.treinaweb.twbiblioteca.obras.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.treinaweb.twbiblioteca.autor.builders.AutorBuilder;
import br.com.treinaweb.twbiblioteca.autor.exceptions.AutorNaoEncontradoException;
import br.com.treinaweb.twbiblioteca.autor.services.AutorService;
import br.com.treinaweb.twbiblioteca.obra.exceptions.ObraNaoEncontradaException;
import br.com.treinaweb.twbiblioteca.obra.mappers.ObraMapper;
import br.com.treinaweb.twbiblioteca.obra.models.Obra;
import br.com.treinaweb.twbiblioteca.obra.repositories.ObraRepository;
import br.com.treinaweb.twbiblioteca.obra.services.ObraService;
import br.com.treinaweb.twbiblioteca.obras.builders.ObraBuilder;
import br.com.treinaweb.twbiblioteca.obras.builders.ObraRequestBuilder;
import br.com.treinaweb.twbiblioteca.obras.builders.ObraResponseBuilder;

@ExtendWith(MockitoExtension.class)
public class ObraServiceTest {

    @Mock
    private ObraRepository repository;

    @Mock
    private ObraMapper mapper;

    @Mock
    private AutorService autorService;

    @InjectMocks
    private ObraService service;

    @Test
    void quandoBuscarTodosEhChamadoDeveRetornarUmaListaDeObraResponse() {
        var model = ObraBuilder.builder().build();
        var modelList = List.of(model);
        var obraResponseEsperada = ObraResponseBuilder.builder().build();
        var obraResponseListEsperada = List.of(obraResponseEsperada);

        when(repository.findAll()).thenReturn(modelList);
        when(mapper.toResponse(model)).thenReturn(obraResponseEsperada);

        var obraResponseListRetornada = service.buscarTodos();

        assertEquals(obraResponseListEsperada.size(), obraResponseListRetornada.size());
        assertEquals(obraResponseEsperada, obraResponseListRetornada.get(0));
    }

    @Test
    void quandoCadastrarEhChamadoComObraRequestComAutorIdInvalidoDeveLancarExceptionAutorNaoEncontradoException() {
        var request = ObraRequestBuilder.builder().build();
        var autorId = request.getAutorId();

        when(autorService.verificarSeExiste(autorId)).thenThrow(AutorNaoEncontradoException.class);

        assertThrows(AutorNaoEncontradoException.class, () -> service.cadastrar(request));
    }

    @Test
    void quandoCadastrarEhChamadoComObraRequestValidoDeveRetornarUmObraResponse() {
        var request = ObraRequestBuilder.builder().build();
        var autor = AutorBuilder.builder().build();
        var autorId = autor.getId();
        var obra = ObraBuilder.builder().build();
        var obraResponseEsperada = ObraResponseBuilder.builder().build();

        when(autorService.verificarSeExiste(autorId)).thenReturn(autor);
        when(mapper.toModel(request)).thenReturn(obra);
        when(repository.save(obra)).thenReturn(obra);
        when(mapper.toResponse(obra)).thenReturn(obraResponseEsperada);

        var obraResponseRetornada = service.cadastrar(request);

        verify(repository, times(1)).save(obra);
        assertEquals(obraResponseEsperada.getId(), obraResponseRetornada.getId());
        assertEquals(obraResponseEsperada.getNome(), obraResponseRetornada.getNome());
        assertEquals(obraResponseEsperada.getQtdPaginas(), obraResponseRetornada.getQtdPaginas());
        assertEquals(obraResponseEsperada.getTipo(), obraResponseRetornada.getTipo());
        assertEquals(obraResponseEsperada.getAutorId(), obraResponseRetornada.getAutorId());
    }

    @Test
    void quandoBuscarPorIdEhChamadoComIdInvalidoDeveLancarExceptionObraNaoEcontradaException() {
        var id = 1L;
        var mensagemEsperada = String.format("Obra com ID %d não encontrada", id);

        var exception = assertThrows(ObraNaoEncontradaException.class, () -> service.buscarPorId(id));
        assertEquals(mensagemEsperada, exception.getMessage());
    }

    @Test
    void quandoBuscarPorIdEhChamadoComIdValidoDeveRetornarUmObraResponse() {
        var obra = ObraBuilder.builder().build();
        var id = obra.getId();
        var obraResponseEsperada = ObraResponseBuilder.builder().build();

        when(repository.findById(id)).thenReturn(Optional.of(obra));
        when(mapper.toResponse(obra)).thenReturn(obraResponseEsperada);

        var obraResponseRetornada = service.buscarPorId(id);

        assertEquals(obraResponseEsperada.getId(), obraResponseRetornada.getId());
        assertEquals(obraResponseEsperada.getNome(), obraResponseRetornada.getNome());
        assertEquals(obraResponseEsperada.getQtdPaginas(), obraResponseRetornada.getQtdPaginas());
        assertEquals(obraResponseEsperada.getTipo(), obraResponseRetornada.getTipo());
        assertEquals(obraResponseEsperada.getAutorId(), obraResponseRetornada.getAutorId());
    }

    @Test
    void quandoAtualizarEhChamadoComIdInvalidoDeveLancarExceptionObraNaoEncontradaException() {
        var id = 1L;
        var request = ObraRequestBuilder.builder().build();
        var mensagemEsperada = String.format("Obra com ID %d não encontrada", id);

        when(repository.findById(id)).thenReturn(Optional.<Obra>empty());

        var exception = assertThrows(ObraNaoEncontradaException.class, () -> service.atualizar(id, request));
        assertEquals(mensagemEsperada, exception.getMessage());
    }

    @Test
    void quandoAtualizarEhChamadoComObraRequestComAutorIdInvalidoDeveLancarExceptionAutorNaoEncontradoException() {
        var id = 1L;
        var request = ObraRequestBuilder.builder().build();
        var autorId = request.getAutorId();
        var obra = ObraBuilder.builder().build();

        when(repository.findById(id)).thenReturn(Optional.of(obra));
        when(autorService.verificarSeExiste(autorId)).thenThrow(AutorNaoEncontradoException.class);

        assertThrows(AutorNaoEncontradoException.class, () -> service.atualizar(id, request));
    }

    @Test
    void quandoAtualizarEhChamadoComDadosValidosDeveRetornarObraResponse() {
        var obra = ObraBuilder.builder().build();
        var autor = AutorBuilder.builder().build();
        var obraEsperada = ObraBuilder.builder().id(null).autor(null).build();
        var id = obra.getId();
        var request = ObraRequestBuilder.builder().build();
        var autorId = request.getAutorId();
        var obraResponseEsperada = ObraResponseBuilder.builder().build();

        when(repository.findById(id)).thenReturn(Optional.of(obra));
        when(autorService.verificarSeExiste(autorId)).thenReturn(autor);
        when(mapper.toModel(request)).thenReturn(obraEsperada);
        when(repository.save(obra)).thenReturn(obra);
        when(mapper.toResponse(obra)).thenReturn(obraResponseEsperada);

        var obraResponseRetornada = service.atualizar(id, request);

        verify(repository, times(1)).save(obra);
        assertEquals(obraResponseEsperada.getId(), obraResponseRetornada.getId());
        assertEquals(obraResponseEsperada.getNome(), obraResponseRetornada.getNome());
        assertEquals(obraResponseEsperada.getQtdPaginas(), obraResponseRetornada.getQtdPaginas());
        assertEquals(obraResponseEsperada.getTipo(), obraResponseRetornada.getTipo());
        assertEquals(obraResponseEsperada.getAutorId(), obraResponseRetornada.getAutorId());
    }

    @Test
    void quandoExcluirPorIdEhChamadoComIdInvalidoDeveLancarExceptonObraNaoEncontradaException() {
        var id = 1L;
        var mensagemEsperada = String.format("Obra com ID %d não encontrada", id);

        when(repository.findById(id)).thenReturn(Optional.<Obra>empty());

        var exception = assertThrows(ObraNaoEncontradaException.class, () -> service.excluirPorId(id));
        assertEquals(mensagemEsperada, exception.getMessage());
    }

    @Test
    void quandoExcluirPorIdForChamadoComIdValidoDeveChamarDeleteDeRepository() {
        var obra = ObraBuilder.builder().build();
        var id = obra.getId();

        when(repository.findById(id)).thenReturn(Optional.of(obra));

        service.excluirPorId(id);

        verify(repository, times(1)).delete(obra);
    }

}
