package br.com.treinaweb.twbiblioteca.emprestimo.builders;

import java.time.LocalDate;
import java.util.Set;

import br.com.treinaweb.twbiblioteca.cliente.builders.ClienteBuilder;
import br.com.treinaweb.twbiblioteca.cliente.models.Cliente;
import br.com.treinaweb.twbiblioteca.emprestimo.models.Emprestimo;
import br.com.treinaweb.twbiblioteca.obras.builders.ObraBuilder;

public class EmprestimoBuilder {

    private Emprestimo emprestimo;

    public static EmprestimoBuilder builder() {
        var builder = new EmprestimoBuilder();

        var id = 1L;
        var cliente = ClienteBuilder.builder().build();
        var obras = Set.of(ObraBuilder.builder().build());
        var dataEmprestimo = LocalDate.now();
        var dataDevolucao = dataEmprestimo.plusDays(3);
        var devolvido = false;
        var emprestimo = new Emprestimo(id, cliente, obras, dataEmprestimo, dataDevolucao, devolvido);

        builder.emprestimo = emprestimo;

        return builder;
    }

    public EmprestimoBuilder id(Long id) {
        emprestimo.setId(id);

        return this;
    }

    public EmprestimoBuilder cliente(Cliente cliente) {
        emprestimo.setCliente(cliente);

        return this;
    }

    public EmprestimoBuilder dataDevolucao(LocalDate dataDevolucao) {
        emprestimo.setDataDevolucao(dataDevolucao);

        return this;
    }

    public Emprestimo build() {
        return emprestimo;
    }

}
