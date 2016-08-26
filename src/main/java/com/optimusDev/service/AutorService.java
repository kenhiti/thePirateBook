package com.optimusDev.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.optimusDev.domain.Autor;
import com.optimusDev.repository.AutorRepository;

@Service
public class AutorService {
	
	@Autowired
	private AutorRepository repository;

	public Autor buscar(Long id) {		
		return repository.findOne(id);
	}

	public Autor salvar(Autor autor) {		
		return repository.save(autor);
	}

	public void atualizar(Autor autor) {
		repository.save(autor);
	}

	public void deletar(Long id) {
		repository.delete(id);		
	}

	public List<Autor> listar() {		
		return repository.findAll();
	}

}
