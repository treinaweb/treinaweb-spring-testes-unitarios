package br.com.treinaweb.twbiblioteca.emprestimo.mappers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.treinaweb.twbiblioteca.emprestimo.builders.EmprestimoBuilder;
import br.com.treinaweb.twbiblioteca.emprestimo.builders.EmprestimoResponseBuilder;
import br.com.treinaweb.twbiblioteca.emprestimo.models.Emprestimo;

public class EmprestimoMapperTest {

    private EmprestimoMapper mapper;

    @BeforeEach
    private void setUp() {
        mapper = new EmprestimoMapper();
    }

    @Test
    void quandoToResponseEhChamadoComEmprestimoInvalidoDeveLancarExceptionIllegalArgumentException() {
        Emprestimo model = null;
        var mensagemEsperada = "Parâmetro model não pode ser nulo";

        var exception = assertThrows(IllegalArgumentException.class, () -> mapper.toResponse(model));
        assertEquals(mensagemEsperada, exception.getMessage());
    }

    @Test
    void quandoToResponseEhChamadoComEmprestimoValidoDeveRetornarUmEmprestimoResponse() {
        var model = EmprestimoBuilder.builder().build();
        var emprestimoResponseEsperado = EmprestimoResponseBuilder.builder().build();

        var emprestimoResponseRetornado = mapper.toResponse(model);

        assertEquals(emprestimoResponseEsperado.getId(), emprestimoResponseRetornado.getId());
        assertEquals(emprestimoResponseEsperado.getClienteId(), emprestimoResponseRetornado.getClienteId());
        assertEquals(emprestimoResponseEsperado.getObras(), emprestimoResponseRetornado.getObras());
        assertEquals(emprestimoResponseEsperado.getDataEmprestimo(), emprestimoResponseRetornado.getDataEmprestimo());
        assertEquals(emprestimoResponseEsperado.getDataDevolucao(), emprestimoResponseRetornado.getDataDevolucao());
        assertEquals(emprestimoResponseEsperado.getDevolvido(), emprestimoResponseRetornado.getDevolvido());
    }

}
