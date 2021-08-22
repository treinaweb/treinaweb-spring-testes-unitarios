package br.com.treinaweb.twbiblioteca.emprestimo.controllers;

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

import br.com.treinaweb.twbiblioteca.emprestimo.dtos.request.EmprestimoRequest;
import br.com.treinaweb.twbiblioteca.emprestimo.dtos.response.EmprestimoResponse;
import br.com.treinaweb.twbiblioteca.emprestimo.services.EmprestimoService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/emprestimos")
@AllArgsConstructor
public class EmprestimoRestController {

    private EmprestimoService service;

    @GetMapping
    public List<EmprestimoResponse> buscarTodos() {
        return service.buscarTodos();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public EmprestimoResponse cadastrar(@RequestBody @Valid EmprestimoRequest request) {
        return service.cadastrar(request);
    }

    @GetMapping("/{id}")
    public EmprestimoResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public EmprestimoResponse atualizar(@PathVariable Long id, @RequestBody @Valid EmprestimoRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void excluirPorId(@PathVariable Long id) {
        service.excluirPorId(id);
    }

}
