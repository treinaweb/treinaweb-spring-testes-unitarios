package br.com.treinaweb.twbiblioteca.autor.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.treinaweb.twbiblioteca.autor.dtos.request.AutorRequest;
import br.com.treinaweb.twbiblioteca.autor.dtos.response.AutorResponse;
import br.com.treinaweb.twbiblioteca.autor.exceptions.AutorNaoEncontradoException;
import br.com.treinaweb.twbiblioteca.autor.mappers.AutorMapper;
import br.com.treinaweb.twbiblioteca.autor.models.Autor;
import br.com.treinaweb.twbiblioteca.autor.repositories.AutorRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AutorService {

    private AutorRepository repository;

    private AutorMapper mapper;

    public List<AutorResponse> buscarTodos() {
        return repository.findAll()
            .stream()
            .map(mapper::toResponse)
            .collect(Collectors.toList());
    }

    public AutorResponse cadastrar(AutorRequest request) {
        var autorParaCadastrar = mapper.toModel(request);

        var autorCadastrado = repository.save(autorParaCadastrar);

        return mapper.toResponse(autorCadastrado);
    }

    public AutorResponse buscarPorId(Long id) {
        var autor = verificarSeExiste(id);

        return mapper.toResponse(autor);
    }

    public AutorResponse atualizar(Long id, AutorRequest request) {
        verificarSeExiste(id);

        var autorParaAtualizar = mapper.toModel(request);
        autorParaAtualizar.setId(id);
        var autorAtualizado = repository.save(autorParaAtualizar);

        return mapper.toResponse(autorAtualizado);
    }

    public void excluirPorId(Long id) {
        var autorParaExcluir = verificarSeExiste(id);

        repository.delete(autorParaExcluir);
    }

    public Autor verificarSeExiste(Long id) {
        var mensagem = String.format("Autor com ID %d não encontrado", id);

        return repository.findById(id)
            .orElseThrow(() -> new AutorNaoEncontradoException(mensagem));
    }

}
