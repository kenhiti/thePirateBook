package com.optimusDev.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.optimusDev.domain.Genero;
import com.optimusDev.repository.GeneroRepository;

@Service
public class GeneroService {
	
	@Autowired
	private GeneroRepository repository;
	

	public List<Genero> listar(){
		return repository.findAll();
	}
	
	public Genero buscar(Long id){
		return repository.findOne(id);
	}
	
	public Genero salvar(Genero genero){
		return repository.save(genero);
	}
	
	public void atualizar(Genero genero){
		repository.save(genero);
	}
	
	public void deletar(Long id){
		repository.delete(id);
	}
}
