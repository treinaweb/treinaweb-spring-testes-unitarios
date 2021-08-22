package br.com.treinaweb.twbiblioteca.dtos.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ValidationErrorResponse extends ErrorResponse {

    private List<FieldError> errors;

}
