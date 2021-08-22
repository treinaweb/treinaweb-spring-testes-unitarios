package br.com.treinaweb.twbiblioteca.autor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.treinaweb.twbiblioteca.autor.models.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long> {

}
