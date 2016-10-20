package com.optimusDev.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.optimusDev.domain.Conta;
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
		Livro livro = repository.findOne(id);
		return livro;
	}
	
	public Livro salvar(Livro livro){
		Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
		Conta conta = (Conta)auth.getPrincipal();
		System.out.println("Usuario Logado : " +conta);
		livro.setConta(conta);
		return repository.save(livro);
	}
	
	public void atualizar(Livro livro){
		repository.save(livro);
	}
	
	public void deletar(Long id){
		repository.delete(id);
	}
}
