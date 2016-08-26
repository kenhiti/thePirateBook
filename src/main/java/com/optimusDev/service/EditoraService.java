package com.optimusDev.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.optimusDev.domain.Editora;
import com.optimusDev.repository.EditoraRepository;

@Service
public class EditoraService {

	@Autowired
	private EditoraRepository repository;
	
	public List<Editora> listar() {
		return repository.findAll();
	}

	public Editora buscar(Long id) {		
		return repository.findOne(id);
	}

	public Editora salvar(Editora editora) {
		return repository.save(editora);
	}
	
	public void atualizar(Editora editora){
		repository.save(editora);
	}
	
	public void deletar(Long id){
		repository.delete(id);
	}

}
