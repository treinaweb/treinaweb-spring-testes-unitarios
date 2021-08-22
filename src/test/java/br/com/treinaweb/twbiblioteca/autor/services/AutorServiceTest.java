package br.com.treinaweb.twbiblioteca.autor.services;

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
import br.com.treinaweb.twbiblioteca.autor.builders.AutorRequestBuilder;
import br.com.treinaweb.twbiblioteca.autor.builders.AutorResponseBuilder;
import br.com.treinaweb.twbiblioteca.autor.exceptions.AutorNaoEncontradoException;
import br.com.treinaweb.twbiblioteca.autor.mappers.AutorMapper;
import br.com.treinaweb.twbiblioteca.autor.models.Autor;
import br.com.treinaweb.twbiblioteca.autor.repositories.AutorRepository;

@ExtendWith(MockitoExtension.class)
public class AutorServiceTest {

    @Mock
    private AutorRepository repository;

    @Mock
    private AutorMapper mapper;

    @InjectMocks
    private AutorService service;

    @Test
    void quandoBuscarTodosEhChamadoDeveRetornarUmaListaDeAutorResponse() {
        var autor = AutorBuilder.builder().build();
        var autorList = List.of(autor);
        var autoResponseEsperado = AutorResponseBuilder.builder().build();
        var autorResponseListEsperado = List.of(autoResponseEsperado);

        when(repository.findAll()).thenReturn(autorList);
        when(mapper.toResponse(autor)).thenReturn(autoResponseEsperado);

        var autoResponseListRetornado = service.buscarTodos();

        assertEquals(autorResponseListEsperado.size(), autoResponseListRetornado.size());
        assertEquals(autorResponseListEsperado.get(0), autoResponseListRetornado.get(0));
    }

    @Test
    void quandoCadastrarEhChamadoDeveRetornarUmAutorResponse() {
        var autor = AutorBuilder.builder().build();
        var autorResponseEsperado = AutorResponseBuilder.builder().build();
        var request = AutorRequestBuilder.builder().build();

        when(mapper.toModel(request)).thenReturn(autor);
        when(repository.save(autor)).thenReturn(autor);
        when(mapper.toResponse(autor)).thenReturn(autorResponseEsperado);

        var autorResponseRetornado = service.cadastrar(request);

        verify(repository, times(1)).save(autor);
        assertEquals(autorResponseEsperado.getId(), autorResponseRetornado.getId());
        assertEquals(autorResponseEsperado.getNome(), autorResponseRetornado.getNome());
        assertEquals(autorResponseEsperado.getDataNascimento(), autorResponseRetornado.getDataNascimento());
        assertEquals(autorResponseEsperado.getDataFalecimento(), autorResponseRetornado.getDataFalecimento());
    }

    @Test
    void quandoBuscarPorIdEhChamadoComIdInvalidoDeveLancarExceptionAutorNaoEncontradoException() {
        var id = 1L;
        var mensagemEsperada = String.format("Autor com ID %d não encontrado", id);

        when(repository.findById(id)).thenReturn(Optional.<Autor>empty());

        var exception = assertThrows(AutorNaoEncontradoException.class, () -> service.buscarPorId(id));
        assertEquals(mensagemEsperada, exception.getMessage());
    }

    @Test
    void quandoBuscarPorIdEhChamadoComIdValidoDeveRetornarUmAutorResponse() {
        var autor = AutorBuilder.builder().build();
        var id = autor.getId();
        var autorResponseEsperado = AutorResponseBuilder.builder().build();

        when(repository.findById(id)).thenReturn(Optional.of(autor));
        when(mapper.toResponse(autor)).thenReturn(autorResponseEsperado);

        var autorResponseRetornado = service.buscarPorId(id);

        assertEquals(autorResponseEsperado.getId(), autorResponseRetornado.getId());
        assertEquals(autorResponseEsperado.getNome(), autorResponseRetornado.getNome());
        assertEquals(autorResponseEsperado.getDataNascimento(), autorResponseRetornado.getDataNascimento());
        assertEquals(autorResponseEsperado.getDataFalecimento(), autorResponseRetornado.getDataFalecimento());
    }

    @Test
    void quandoAtualizarEhChamadoComIdInvalidoDeveLancarExceptionAutorNaoEncontradoExcepton() {
        var id = 1L;
        var request = AutorRequestBuilder.builder().build();
        var mensagemEsperada = String.format("Autor com ID %d não encontrado", id);

        when(repository.findById(id)).thenReturn(Optional.<Autor>empty());

        var exception = assertThrows(AutorNaoEncontradoException.class, () -> service.atualizar(id, request));
        assertEquals(mensagemEsperada, exception.getMessage());
    }

    @Test
    void quandoAtualizarEhChamadoComDadosValidosDeveRetornarUmAutorResponse() {
        var autor = AutorBuilder.builder().build();
        var id = autor.getId();
        var request = AutorRequestBuilder.builder().build();
        var autorResponseEsperado = AutorResponseBuilder.builder().build();

        when(repository.findById(id)).thenReturn(Optional.of(autor));
        when(repository.save(autor)).thenReturn(autor);
        when(mapper.toModel(request)).thenReturn(autor);
        when(mapper.toResponse(autor)).thenReturn(autorResponseEsperado);

        var autorResponseRetornado = service.atualizar(id, request);

        verify(repository, times(1)).save(autor);
        assertEquals(autorResponseEsperado.getId(), autorResponseRetornado.getId());
        assertEquals(autorResponseEsperado.getNome(), autorResponseRetornado.getNome());
        assertEquals(autorResponseEsperado.getDataNascimento(), autorResponseRetornado.getDataNascimento());
        assertEquals(autorResponseEsperado.getDataFalecimento(), autorResponseRetornado.getDataFalecimento());
    }

    @Test
    void quandoExcluirPorIdEhChamadoComIdInvalidoDeveLancarExceptionAutorNaoEncontradoException() {
        var id = 1L;
        var mensagemEsperada = String.format("Autor com ID %d não encontrado", id);

        when(repository.findById(id)).thenReturn(Optional.<Autor>empty());

        var exception = assertThrows(AutorNaoEncontradoException.class, () -> service.excluirPorId(id));
        assertEquals(mensagemEsperada, exception.getMessage());
    }

    @Test
    void quandoExcluirPorIdEhChamadoComIdValidoDeveChamarDeleteDeRepository() {
        var autor = AutorBuilder.builder().build();
        var id = autor.getId();

        when(repository.findById(id)).thenReturn(Optional.of(autor));

        service.excluirPorId(id);

        verify(repository, times(1)).delete(autor);
    }

}
