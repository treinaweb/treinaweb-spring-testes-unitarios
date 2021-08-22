package br.com.treinaweb.twbiblioteca.cliente.controllers;

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

import br.com.treinaweb.twbiblioteca.cliente.dtos.request.ClienteRequest;
import br.com.treinaweb.twbiblioteca.cliente.dtos.response.ClienteResponse;
import br.com.treinaweb.twbiblioteca.cliente.services.ClienteService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/clientes")
@AllArgsConstructor
public class ClienteRestController {

    private ClienteService service;

    @GetMapping
    public List<ClienteResponse> buscarTodos() {
        return service.buscarTodos();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ClienteResponse cadastrar(@RequestBody @Valid ClienteRequest request) {
        return service.cadastrar(request);
    }

    @GetMapping("/{id}")
    public ClienteResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ClienteResponse atualizar(@PathVariable Long id, @RequestBody @Valid ClienteRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void excluirPorId(@PathVariable Long id) {
        service.excluirPorId(id);
    }

}
