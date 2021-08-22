package br.com.treinaweb.twbiblioteca.emprestimo.builders;

import java.time.LocalDate;
import java.util.Set;

import br.com.treinaweb.twbiblioteca.cliente.builders.ClienteBuilder;
import br.com.treinaweb.twbiblioteca.emprestimo.dtos.response.EmprestimoResponse;
import br.com.treinaweb.twbiblioteca.obras.builders.ObraBuilder;

public class EmprestimoResponseBuilder {

    private EmprestimoResponse emprestimoResponse;

    public static EmprestimoResponseBuilder builder() {
        var builder = new EmprestimoResponseBuilder();

        var id = 1L;
        var clienteId = ClienteBuilder.builder().build().getId();
        var obras = Set.of(ObraBuilder.builder().build().getId());
        var dataEmprestimo = LocalDate.now();
        var dataDevolucao = dataEmprestimo.plusDays(3);
        var devolvido = false;
        var emprestimoResponse = new EmprestimoResponse(id, clienteId, obras, dataEmprestimo, dataDevolucao, devolvido);

        builder.emprestimoResponse = emprestimoResponse;

        return builder;
    }

    public EmprestimoResponseBuilder dataDevolucao(LocalDate dataDevolucao) {
        emprestimoResponse.setDataDevolucao(dataDevolucao);

        return this;
    }

    public EmprestimoResponse build() {
        return emprestimoResponse;
    }

}
