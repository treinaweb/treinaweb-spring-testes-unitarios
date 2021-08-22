package br.com.treinaweb.twbiblioteca.emprestimo.mappers;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.treinaweb.twbiblioteca.emprestimo.dtos.response.EmprestimoResponse;
import br.com.treinaweb.twbiblioteca.emprestimo.models.Emprestimo;
import br.com.treinaweb.twbiblioteca.obra.models.Obra;

@Component
public class EmprestimoMapper {

    public EmprestimoResponse toResponse(Emprestimo model) {
        if (model == null) {
            throw new IllegalArgumentException("Parâmetro model não pode ser nulo");
        }

        var response = new EmprestimoResponse();

        response.setId(model.getId());
        response.setClienteId(model.getCliente().getId());
        var obras = model.getObras()
            .stream()
            .map(Obra::getId)
            .collect(Collectors.toSet());
        response.setObras(obras);
        response.setDataEmprestimo(model.getDataEmprestimo());
        response.setDataDevolucao(model.getDataDevolucao());
        response.setDevolvido(model.getDevolvido());

        return response;
    }

}
