package br.com.treinaweb.twbiblioteca.emprestimo.dtos.response;

import java.time.LocalDate;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmprestimoResponse {

    private Long id;

    private Long clienteId;

    private Set<Long> obras;

    private LocalDate dataEmprestimo;

    private LocalDate dataDevolucao;

    private Boolean devolvido;

}
