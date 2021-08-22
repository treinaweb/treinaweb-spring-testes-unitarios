package br.com.treinaweb.twbiblioteca.emprestimo.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.treinaweb.twbiblioteca.cliente.models.Cliente;
import br.com.treinaweb.twbiblioteca.cliente.services.ClienteService;
import br.com.treinaweb.twbiblioteca.emprestimo.dtos.request.EmprestimoRequest;
import br.com.treinaweb.twbiblioteca.emprestimo.dtos.response.EmprestimoResponse;
import br.com.treinaweb.twbiblioteca.emprestimo.exceptions.EmprestimoNaoEncontradoException;
import br.com.treinaweb.twbiblioteca.emprestimo.mappers.EmprestimoMapper;
import br.com.treinaweb.twbiblioteca.emprestimo.models.Emprestimo;
import br.com.treinaweb.twbiblioteca.emprestimo.repositories.EmprestimoRepository;
import br.com.treinaweb.twbiblioteca.obra.models.Obra;
import br.com.treinaweb.twbiblioteca.obra.services.ObraService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmprestimoService {

    private EmprestimoRepository repository;

    private EmprestimoMapper mapper;

    private ClienteService clienteService;

    private ObraService obraService;

    public List<EmprestimoResponse> buscarTodos() {
        return repository.findAll()
            .stream()
            .map(mapper::toResponse)
            .collect(Collectors.toList());
    }

    public EmprestimoResponse cadastrar(EmprestimoRequest request) {
        var cliente = clienteService.verificaSeExiste(request.getClienteId());
        var obras = request.getObras()
            .stream()
            .map(obraService::verificaSeExiste)
            .collect(Collectors.toSet());

        var emprestimoParaCadastrar = criar(cliente, obras);

        var emprestimoCadastrado = repository.save(emprestimoParaCadastrar);

        return mapper.toResponse(emprestimoCadastrado);
    }

    public EmprestimoResponse buscarPorId(Long id) {
        var emprestimo = verificaSeExiste(id);

        return mapper.toResponse(emprestimo);
    }

    public EmprestimoResponse atualizar(Long id, EmprestimoRequest request) {
        var emprestimoParaAtualizar = verificaSeExiste(id);

        var cliente = clienteService.verificaSeExiste(request.getClienteId());
        var obras = request.getObras()
            .stream()
            .map(obraService::verificaSeExiste)
            .collect(Collectors.toSet());

        emprestimoParaAtualizar.setCliente(cliente);
        emprestimoParaAtualizar.setObras(obras);

        var emprestimoAtualizado = repository.save(emprestimoParaAtualizar);

        return mapper.toResponse(emprestimoAtualizado);
    }

    public void excluirPorId(Long id) {
        var emprestimo = verificaSeExiste(id);

        repository.delete(emprestimo);
    }

    protected Emprestimo criar(Cliente cliente, Set<Obra> obras) {
        var emprestimo = new Emprestimo();

        var dataEmprestimo = LocalDate.now();
        var diasParaDevolucao = cliente.getReputacao().obterDiasParaDevolucao();
        var dataDevolucao = dataEmprestimo.plusDays(diasParaDevolucao);

        emprestimo.setCliente(cliente);
        emprestimo.setObras(obras);
        emprestimo.setDataEmprestimo(dataEmprestimo);
        emprestimo.setDataDevolucao(dataDevolucao);
        emprestimo.setDevolvido(false);

        return emprestimo;
    }

    private Emprestimo verificaSeExiste(Long id) {
        var mensagem = String.format("Emprestimo com ID %d não encontrado", id);

        return repository.findById(id)
            .orElseThrow(() -> new EmprestimoNaoEncontradoException(mensagem));
    }

}
