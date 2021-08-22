package br.com.treinaweb.twbiblioteca.obra.exceptions;

import javax.persistence.EntityNotFoundException;

public class ObraNaoEncontradaException extends EntityNotFoundException {

    public ObraNaoEncontradaException(String message) {
        super(message);
    }

}
