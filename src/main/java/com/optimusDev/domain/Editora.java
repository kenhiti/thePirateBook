package com.optimusDev.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="editora")
public class Editora implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idEditora;
	private String nome;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_editora")
	public Long getIdEditora() {
		return idEditora;
	}
	public void setIdEditora(Long idEditora) {
		this.idEditora = idEditora;
	}
	
	@NotNull
	@Column(name="nome")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idEditora == null) ? 0 : idEditora.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Editora other = (Editora) obj;
		if (idEditora == null) {
			if (other.idEditora != null)
				return false;
		} else if (!idEditora.equals(other.idEditora))
			return false;
		return true;
	}
	
	
}
