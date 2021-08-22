package br.com.treinaweb.twbiblioteca.emprestimo.dtos.request;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmprestimoRequest {

    @NotNull
    @Positive
    private Long clienteId;

    @NotNull
    @NotEmpty
    private Set<Long> obras;

}
