package com.optimusDev.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.optimusDev.domain.Conta;
import com.optimusDev.repository.ContaRepository;

@Service
public class ContaService {
	
	@Autowired
	private ContaRepository repository;
	
	public List<Conta> listar(){
		return repository.findAll();
	}
	
	public Conta buscar(Long id){
		return repository.findOne(id);
	}
	
	public Conta salvar(Conta conta){
		return repository.save(conta);
	}
	
	public void atualizar(Conta conta){
		repository.save(conta);
	}
	
	public void deletar(Long id){
		repository.delete(id);
	}

	public Conta buscarPorEmail(String email) {
		return repository.findByEmailEquals(email);
	}

}
