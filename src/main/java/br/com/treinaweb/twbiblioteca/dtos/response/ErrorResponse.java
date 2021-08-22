package br.com.treinaweb.twbiblioteca.dtos.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private Integer codigo;

    private String status;

    private LocalDateTime timestamp;

    private String causa;

}
