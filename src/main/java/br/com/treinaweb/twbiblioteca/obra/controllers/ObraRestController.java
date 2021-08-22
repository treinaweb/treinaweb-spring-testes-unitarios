package br.com.treinaweb.twbiblioteca.obra.controllers;

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

import br.com.treinaweb.twbiblioteca.obra.dtos.request.ObraRequest;
import br.com.treinaweb.twbiblioteca.obra.dtos.response.ObraResponse;
import br.com.treinaweb.twbiblioteca.obra.services.ObraService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/obras")
@AllArgsConstructor
public class ObraRestController {

    private ObraService service;

    @GetMapping
    public List<ObraResponse> buscarTodos() {
        return service.buscarTodos();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ObraResponse cadastrar(@RequestBody @Valid ObraRequest request) {
        return service.cadastrar(request);
    }

    @GetMapping("/{id}")
    public ObraResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ObraResponse atualizar(@PathVariable Long id, @RequestBody @Valid ObraRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void excluirPorId(@PathVariable Long id) {
        service.excluirPorId(id);
    }

}
