package br.com.treinaweb.twbiblioteca.cliente.dtos.response;

import java.time.LocalDate;

import br.com.treinaweb.twbiblioteca.cliente.enums.Reputacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponse {

    private Long id;

    private String nome;

    private LocalDate dataNascimento;

    private String cpf;

    private Reputacao reputacao;

}
