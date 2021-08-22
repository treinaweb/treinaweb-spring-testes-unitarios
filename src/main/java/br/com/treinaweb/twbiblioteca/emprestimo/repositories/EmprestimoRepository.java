package br.com.treinaweb.twbiblioteca.emprestimo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.treinaweb.twbiblioteca.emprestimo.models.Emprestimo;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long>{

}
