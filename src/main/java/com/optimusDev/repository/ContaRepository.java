package com.optimusDev.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.optimusDev.domain.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {

	Conta findByEmailEquals(String apiKey);

}
