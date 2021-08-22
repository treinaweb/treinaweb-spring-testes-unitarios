package br.com.treinaweb.twbiblioteca.autor.exceptions;

import javax.persistence.EntityNotFoundException;

public class AutorNaoEncontradoException extends EntityNotFoundException {

    public AutorNaoEncontradoException(String message) {
        super(message);
    }

}
