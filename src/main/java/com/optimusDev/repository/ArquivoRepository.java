package com.optimusDev.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.optimusDev.domain.Arquivo;

public interface ArquivoRepository extends JpaRepository<Arquivo, Long> {

}
