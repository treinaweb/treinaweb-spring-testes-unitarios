package br.com.treinaweb.twbiblioteca.obra.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.treinaweb.twbiblioteca.obra.models.Obra;

public interface ObraRepository extends JpaRepository<Obra, Long> {

}
