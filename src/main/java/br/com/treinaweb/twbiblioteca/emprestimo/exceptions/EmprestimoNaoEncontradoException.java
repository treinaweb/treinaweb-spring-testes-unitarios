package br.com.treinaweb.twbiblioteca.emprestimo.exceptions;

import javax.persistence.EntityNotFoundException;

public class EmprestimoNaoEncontradoException extends EntityNotFoundException {

    public EmprestimoNaoEncontradoException(String message) {
        super(message);
    }

}
