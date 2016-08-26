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
@Table(name="autor")
public class Autor implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long idAutor;
	private String nome;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getIdAutor() {
		return idAutor;
	}
	public void setIdAutor(Long idAutor) {
		this.idAutor = idAutor;
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
		result = prime * result + ((idAutor == null) ? 0 : idAutor.hashCode());
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
		Autor other = (Autor) obj;
		if (idAutor == null) {
			if (other.idAutor != null)
				return false;
		} else if (!idAutor.equals(other.idAutor))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return this.idAutor.toString();
	}
}
