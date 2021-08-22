package br.com.treinaweb.twbiblioteca.autor.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.treinaweb.twbiblioteca.autor.dtos.request.AutorRequest;
import br.com.treinaweb.twbiblioteca.autor.dtos.response.AutorResponse;
import br.com.treinaweb.twbiblioteca.autor.services.AutorService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/autores")
@AllArgsConstructor
public class AutorRestController {

    private AutorService service;

    @GetMapping
    public List<AutorResponse> buscarTodos() {
        return service.buscarTodos();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public AutorResponse cadastrar(@RequestBody @Valid AutorRequest request) {
        return service.cadastrar(request);
    }

    @GetMapping("/{id}")
    public AutorResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public AutorResponse atualizar(@PathVariable Long id, @RequestBody @Valid AutorRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void excluirPorId(@PathVariable Long id) {
        service.excluirPorId(id);
    }

}
