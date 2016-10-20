package com.optimusDev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.optimusDev.domain.Arquivo;
import com.optimusDev.repository.ArquivoRepository;

@Service
public class ArquivoService {
	
	@Autowired
	private ArquivoRepository repository;
	
	public Arquivo salvar(Arquivo arquivo){		
		return repository.save(arquivo);
	}
	
	public Arquivo buscar(Long id){
		return repository.findOne(id);
	}
	
	
}
