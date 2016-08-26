package com.optimusDev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.optimusDev.domain.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long>{
	
	public Autor findByNomeEquals(String nome);

	@Query(value="select nome from autor where nome = :nome", nativeQuery=true)
	public String buscarNomeAutor(@Param(value = "nome") String nome);
}
