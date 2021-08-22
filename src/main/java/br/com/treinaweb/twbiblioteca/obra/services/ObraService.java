package br.com.treinaweb.twbiblioteca.obra.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.treinaweb.twbiblioteca.autor.services.AutorService;
import br.com.treinaweb.twbiblioteca.obra.dtos.request.ObraRequest;
import br.com.treinaweb.twbiblioteca.obra.dtos.response.ObraResponse;
import br.com.treinaweb.twbiblioteca.obra.exceptions.ObraNaoEncontradaException;
import br.com.treinaweb.twbiblioteca.obra.mappers.ObraMapper;
import br.com.treinaweb.twbiblioteca.obra.models.Obra;
import br.com.treinaweb.twbiblioteca.obra.repositories.ObraRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ObraService {

    private ObraRepository repository;

    private ObraMapper mapper;

    private AutorService autorService;

    public List<ObraResponse> buscarTodos() {
        return repository.findAll()
            .stream()
            .map(mapper::toResponse)
            .collect(Collectors.toList());
    }

    public ObraResponse cadastrar(ObraRequest request) {
        var autor = autorService.verificarSeExiste(request.getAutorId());

        var obraParaCadastrar = mapper.toModel(request);
        obraParaCadastrar.setAutor(autor);
        var obraCadastrada = repository.save(obraParaCadastrar);

        return mapper.toResponse(obraCadastrada);
    }

    public ObraResponse buscarPorId(Long id) {
        var obra = verificaSeExiste(id);

        return mapper.toResponse(obra);
    }

    public ObraResponse atualizar(Long id, ObraRequest request) {
        verificaSeExiste(id);

        var autor = autorService.verificarSeExiste(request.getAutorId());
        var obraParaAtualizar = mapper.toModel(request);
        obraParaAtualizar.setId(id);
        obraParaAtualizar.setAutor(autor);
        var obraAtualizada = repository.save(obraParaAtualizar);

        return mapper.toResponse(obraAtualizada);
    }

    public void excluirPorId(Long id) {
        var autor = verificaSeExiste(id);

        repository.delete(autor);
    }

    public Obra verificaSeExiste(Long id) {
        var mensagem = String.format("Obra com ID %d não encontrada", id);

        return repository.findById(id)
            .orElseThrow(() -> new ObraNaoEncontradaException(mensagem));
    }

}
