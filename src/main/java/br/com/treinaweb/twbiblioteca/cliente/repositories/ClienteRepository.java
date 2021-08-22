package br.com.treinaweb.twbiblioteca.cliente.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.treinaweb.twbiblioteca.cliente.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
