package br.com.treinaweb.twbiblioteca.cliente.exceptions;

import javax.persistence.EntityNotFoundException;

public class ClienteNaoEncontradoException extends EntityNotFoundException {

    public ClienteNaoEncontradoException(String message) {
        super(message);
    }

}
