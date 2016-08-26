package com.optimusDev.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.optimusDev.domain.Livro;
import com.optimusDev.repository.LivroRepository;

@Service
public class LivroService {

	@Autowired
	private LivroRepository repository;
	
	public List<Livro> listar(){
		return repository.findAll();
	}
	
	public Livro buscar(Long id){
		return repository.findOne(id);
	}
	
	public Livro salvar(Livro livro){
		return repository.save(livro);
	}
	
	public void atualizar(Livro livro){
		repository.save(livro);
	}
	
	public void deletar(Long id){
		repository.delete(id);
	}
}
