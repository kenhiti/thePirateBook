package com.optimusDev.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.optimusDev.domain.Categoria;
import com.optimusDev.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repository;
	

	public List<Categoria> listar() {		
		return repository.findAll();
	}

	public Categoria buscar(Long id) {		
		return repository.findOne(id);
	}

	public Categoria salvar(Categoria categoria) {		
		return repository.save(categoria);
	}

	public void atualizar(Categoria categoria) {
		repository.save(categoria);		
	}

	public void deletar(Long id) {
		repository.delete(id);		
	}

}
