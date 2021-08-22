package br.com.treinaweb.twbiblioteca.emprestimo.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.treinaweb.twbiblioteca.cliente.builders.ClienteBuilder;
import br.com.treinaweb.twbiblioteca.cliente.enums.Reputacao;
import br.com.treinaweb.twbiblioteca.cliente.exceptions.ClienteNaoEncontradoException;
import br.com.treinaweb.twbiblioteca.cliente.services.ClienteService;
import br.com.treinaweb.twbiblioteca.emprestimo.builders.EmprestimoBuilder;
import br.com.treinaweb.twbiblioteca.emprestimo.builders.EmprestimoRequestBuilder;
import br.com.treinaweb.twbiblioteca.emprestimo.builders.EmprestimoResponseBuilder;
import br.com.treinaweb.twbiblioteca.emprestimo.exceptions.EmprestimoNaoEncontradoException;
import br.com.treinaweb.twbiblioteca.emprestimo.mappers.EmprestimoMapper;
import br.com.treinaweb.twbiblioteca.emprestimo.models.Emprestimo;
import br.com.treinaweb.twbiblioteca.emprestimo.repositories.EmprestimoRepository;
import br.com.treinaweb.twbiblioteca.obra.exceptions.ObraNaoEncontradaException;
import br.com.treinaweb.twbiblioteca.obra.services.ObraService;
import br.com.treinaweb.twbiblioteca.obras.builders.ObraBuilder;

@ExtendWith(MockitoExtension.class)
public class EmprestimoServiceTest {

    @Mock
    private EmprestimoRepository repository;

    @Mock
    private EmprestimoMapper mapper;

    @Mock
    private ClienteService clienteService;

    @Mock
    private ObraService obraService;

    @InjectMocks
    private EmprestimoService service;

    @Test
    void quandoBuscarTodosEhChamadoDeveRetornarUmaListaDeEmprestimoResponse() {
        var emprestimo = EmprestimoBuilder.builder().build();
        var emprestimoList = List.of(emprestimo);
        var emprestimoResponseEsperado = EmprestimoResponseBuilder.builder().build();
        var emprestimoResponseEsperadoList = List.of(emprestimoResponseEsperado);

        when(repository.findAll()).thenReturn(emprestimoList);
        when(mapper.toResponse(emprestimo)).thenReturn(emprestimoResponseEsperado);

        var emprestimoResponseRetornadoList = service.buscarTodos();

        verify(repository, times(1)).findAll();
        assertEquals(emprestimoResponseEsperadoList.size(), emprestimoResponseRetornadoList.size());
        assertEquals(emprestimoResponseEsperado, emprestimoResponseEsperadoList.get(0));
    }

    @Test
    void quandoCadastrarEhChamadoComEmprestimoRequestValidoDeveRetornarUmEmprestimoResponse() {
        var cliente = ClienteBuilder.builder().build();
        var clienteId = cliente.getId();

        var obra = ObraBuilder.builder().build();
        var obraId = obra.getId();

        var request = EmprestimoRequestBuilder.builder().build();
        var modelParaCadastrar = EmprestimoBuilder.builder().id(null).build();
        var model = EmprestimoBuilder.builder().build();
        var emprestimoResponseEsperado = EmprestimoResponseBuilder.builder().build();

        when(clienteService.verificaSeExiste(clienteId)).thenReturn(cliente);
        when(obraService.verificaSeExiste(obraId)).thenReturn(obra);
        when(repository.save(modelParaCadastrar)).thenReturn(model);
        when(mapper.toResponse(model)).thenReturn(emprestimoResponseEsperado);

        var emprestimoResponseRetornado = service.cadastrar(request);

        verify(repository, times(1)).save(modelParaCadastrar);
        assertEquals(emprestimoResponseEsperado.getId(), emprestimoResponseRetornado.getId());
        assertEquals(emprestimoResponseEsperado.getClienteId(), emprestimoResponseRetornado.getClienteId());
        assertEquals(emprestimoResponseEsperado.getObras(), emprestimoResponseRetornado.getObras());
        assertEquals(emprestimoResponseEsperado.getDataEmprestimo(), emprestimoResponseRetornado.getDataEmprestimo());
        assertEquals(emprestimoResponseEsperado.getDataDevolucao(), emprestimoResponseRetornado.getDataDevolucao());
        assertEquals(emprestimoResponseEsperado.getDevolvido(), emprestimoResponseRetornado.getDevolvido());
    }

    @Test
    void quandoCadastrarEhChamadoComEmprestimoRequestComClienteIdInvalidoDeveLancarExceptionClienteNaoEncontradoException() {
        var clienteId = 1L;
        var request = EmprestimoRequestBuilder.builder().build();

        when(clienteService.verificaSeExiste(clienteId)).thenThrow(ClienteNaoEncontradoException.class);

        assertThrows(ClienteNaoEncontradoException.class, () -> service.cadastrar(request));
    }

    @Test
    void quandoCadastrarEhChamadoComEmprestimoRequestComAlgumObraIdInvalidoDeveLancarExceptionObraNaoEncontradaException() {
        var obraId = 1L;
        var request = EmprestimoRequestBuilder.builder().build();
        var clienteId = 1L;
        var cliente = ClienteBuilder.builder().build();

        when(clienteService.verificaSeExiste(clienteId)).thenReturn(cliente);
        when(obraService.verificaSeExiste(obraId)).thenThrow(ObraNaoEncontradaException.class);

        assertThrows(ObraNaoEncontradaException.class, () -> service.cadastrar(request));
    }

    @Test
    void quandoCriarEhChamadoComClienteDeReputacaoRuimDeveRetornarEmprestimoComUmDiaParaDevolucao() {
        var cliente = ClienteBuilder.builder()
            .reputacao(Reputacao.RUIM)
            .build();
        var obra = ObraBuilder.builder().build();
        var obras = Set.of(obra);
        var emprestimoEsperado = EmprestimoBuilder.builder()
            .dataDevolucao(LocalDate.now().plusDays(1))
            .build();

        var emprestimoRetornado = service.criar(cliente, obras);

        assertEquals(emprestimoEsperado.getDataDevolucao(), emprestimoRetornado.getDataDevolucao());
    }

    @Test
    void quandoCriarEhChamadoComClienteDeReputacaoRegularDeveRetornarEmprestimoComTresDiaParaDevolucao() {
        var cliente = ClienteBuilder.builder()
            .reputacao(Reputacao.REGULAR)
            .build();
        var obra = ObraBuilder.builder().build();
        var obras = Set.of(obra);
        var emprestimoEsperado = EmprestimoBuilder.builder()
            .dataDevolucao(LocalDate.now().plusDays(3))
            .build();

        var emprestimoRetornado = service.criar(cliente, obras);

        assertEquals(emprestimoEsperado.getDataDevolucao(), emprestimoRetornado.getDataDevolucao());
    }

    @Test
    void quandoCriarEhChamadoComClienteDeReputacaoBoaDeveRetornarEmprestimoComCincoDiaParaDevolucao() {
        var cliente = ClienteBuilder.builder()
            .reputacao(Reputacao.BOA)
            .build();
        var obra = ObraBuilder.builder().build();
        var obras = Set.of(obra);
        var emprestimoEsperado = EmprestimoBuilder.builder()
            .dataDevolucao(LocalDate.now().plusDays(5))
            .build();

        var emprestimoRetornado = service.criar(cliente, obras);

        assertEquals(emprestimoEsperado.getDataDevolucao(), emprestimoRetornado.getDataDevolucao());
    }

    @Test
    void quandoBuscarPorIdEhChamadoComIdInvalidoDeveLacarExceptionEmprestimoNaoEncontradoException() {
        var id = 1L;
        var mensagemEsperada = String.format("Emprestimo com ID %d não encontrado", id);

        when(repository.findById(id)).thenReturn(Optional.<Emprestimo>empty());

        var exception = assertThrows(EmprestimoNaoEncontradoException.class, () -> service.buscarPorId(id));
        assertEquals(mensagemEsperada, exception.getMessage());
    }

    @Test
    void quandoBuscarPorIdEhChamadoComIdValidoDeveRetornarEmprestimoResonse() {
        var emprestimo = EmprestimoBuilder.builder().build();
        var id = emprestimo.getId();
        var emprestimoResponseEsperado = EmprestimoResponseBuilder.builder().build();

        when(repository.findById(id)).thenReturn(Optional.of(emprestimo));
        when(mapper.toResponse(emprestimo)).thenReturn(emprestimoResponseEsperado);

        var emprestimoResponseRetornado = service.buscarPorId(id);

        assertEquals(emprestimoResponseEsperado.getId(), emprestimoResponseRetornado.getId());
        assertEquals(emprestimoResponseEsperado.getClienteId(), emprestimoResponseRetornado.getClienteId());
        assertEquals(emprestimoResponseEsperado.getObras(), emprestimoResponseRetornado.getObras());
        assertEquals(emprestimoResponseEsperado.getDataEmprestimo(), emprestimoResponseRetornado.getDataEmprestimo());
        assertEquals(emprestimoResponseEsperado.getDataDevolucao(), emprestimoResponseRetornado.getDataDevolucao());
        assertEquals(emprestimoResponseEsperado.getDevolvido(), emprestimoResponseRetornado.getDevolvido());
    }

    @Test
    void quandoAtualizarEhChamadoComIdInvalidoDeveLancarExceptionEmprestimoNaoEncontradoException() {
        var id = 1L;
        var request = EmprestimoRequestBuilder.builder().build();
        var mensagemEsperada = String.format("Emprestimo com ID %d não encontrado", id);

        when(repository.findById(id)).thenReturn(Optional.<Emprestimo>empty());

        var exception = assertThrows(EmprestimoNaoEncontradoException.class, () -> service.atualizar(id, request));
        assertEquals(mensagemEsperada, exception.getMessage());
    }

    @Test
    void quandoAtualizarEhChamadoComClienteIdInvalidoDeveLancarExceptionClienteNaoEncontradoException() {
        var model = EmprestimoBuilder.builder().build();
        var id = model.getId();
        var request = EmprestimoRequestBuilder.builder().build();
        var clienteId = request.getClienteId();

        when(repository.findById(id)).thenReturn(Optional.of(model));
        when(clienteService.verificaSeExiste(clienteId)).thenThrow(ClienteNaoEncontradoException.class);

        assertThrows(ClienteNaoEncontradoException.class, () -> service.atualizar(id, request));
    }

    @Test
    void quandoAtualizarEhChamadoComObraIdInvalidoDeveLancarExceptionObraNaoEncontradaException() {
        var model = EmprestimoBuilder.builder().build();
        var id = model.getId();
        var request = EmprestimoRequestBuilder.builder().build();
        var clienteId = request.getClienteId();
        var cliente = ClienteBuilder.builder().build();
        var obraId = 1L;

        when(repository.findById(id)).thenReturn(Optional.of(model));
        when(clienteService.verificaSeExiste(clienteId)).thenReturn(cliente);
        when(obraService.verificaSeExiste(obraId)).thenThrow(ObraNaoEncontradaException.class);

        assertThrows(ObraNaoEncontradaException.class, () -> service.atualizar(id, request));
    }

    @Test
    void quandoAtualizarEhChamadoComDadosValidosDeveRetornarUmEmprestimoService() {
        var cliente = ClienteBuilder.builder().build();
        var clienteId = cliente.getId();

        var obra = ObraBuilder.builder().build();
        var obraId = obra.getId();

        var request = EmprestimoRequestBuilder.builder().build();
        var model = EmprestimoBuilder.builder().build();
        var emprestimoResponseEsperado = EmprestimoResponseBuilder.builder().build();
        var id = model.getId();

        when(repository.findById(id)).thenReturn(Optional.of(model));
        when(clienteService.verificaSeExiste(clienteId)).thenReturn(cliente);
        when(obraService.verificaSeExiste(obraId)).thenReturn(obra);
        when(repository.save(model)).thenReturn(model);
        when(mapper.toResponse(model)).thenReturn(emprestimoResponseEsperado);

        var emprestimoResponseRetornado = service.atualizar(id, request);

        verify(repository, times(1)).save(model);
        assertEquals(emprestimoResponseEsperado.getId(), emprestimoResponseRetornado.getId());
        assertEquals(emprestimoResponseEsperado.getClienteId(), emprestimoResponseRetornado.getClienteId());
        assertEquals(emprestimoResponseEsperado.getObras(), emprestimoResponseRetornado.getObras());
        assertEquals(emprestimoResponseEsperado.getDataEmprestimo(), emprestimoResponseRetornado.getDataEmprestimo());
        assertEquals(emprestimoResponseEsperado.getDataDevolucao(), emprestimoResponseRetornado.getDataDevolucao());
        assertEquals(emprestimoResponseEsperado.getDevolvido(), emprestimoResponseRetornado.getDevolvido());
    }

    @Test
    void quandoExcluirPorIdEhChamadoComIdInvalidoDeveLancarExceptionEmprestimoNaoEncontradoException() {
        var id = 1L;
        var mensagemEsperada = String.format("Emprestimo com ID %d não encontrado", id);

        when(repository.findById(id)).thenReturn(Optional.<Emprestimo>empty());

        var exception = assertThrows(EmprestimoNaoEncontradoException.class, () -> service.excluirPorId(id));
        assertEquals(mensagemEsperada, exception.getMessage());
    }

    @Test
    void quandoExcluirPorIdEhChamadoComIdValidoDeveChamarDeleteDeRepository() {
        var emprestimo = EmprestimoBuilder.builder().build();
        var id = emprestimo.getId();

        when(repository.findById(id)).thenReturn(Optional.of(emprestimo));

        service.excluirPorId(id);

        verify(repository, times(1)).delete(emprestimo);
    }

}
