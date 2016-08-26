package com.optimusDev.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.optimusDev.domain.Perfil;
import com.optimusDev.repository.PerfilRepository;

@Service
public class PerfilService {

	@Autowired
	private PerfilRepository repository;
	
	public List<Perfil> listar(){
		return repository.findAll();
	}
	
	public Perfil buscar(Long id){
		return repository.findOne(id);
	}
	
	public Perfil salvar(Perfil perfil){
		return repository.save(perfil);
	}
	
	public void atualizar(Perfil perfil){
		repository.save(perfil);
	}
	
	public void deletar(Long id){
		repository.delete(id);
	}
}
