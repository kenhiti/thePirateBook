package com.optimusDev.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;


@Entity
@Table(name="perfil")
public class Perfil implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long idPerfil;
	private String descricao;
	private Date inicioValidade;	
	private Date fimValidade;
	
	public Perfil() {
		
	}
	
	public Perfil(String descricao, Date inicioValidade){
		this.descricao = descricao;
		this.inicioValidade = inicioValidade;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_perfil")
	public Long getIdPerfil() {
		return idPerfil;
	}
	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}
	
	@NotNull
	@Column(name="descricao")
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name="inicio_validade")
	public Date getInicioValidade() {
		return inicioValidade;
	}
	public void setInicioValidade(Date inicioValidade) {
		this.inicioValidade = inicioValidade;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="fim_validade")
	public Date getFimValidade() {
		return fimValidade;
	}
	public void setFimValidade(Date fimValidade) {
		this.fimValidade = fimValidade;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idPerfil == null) ? 0 : idPerfil.hashCode());
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
		Perfil other = (Perfil) obj;
		if (idPerfil == null) {
			if (other.idPerfil != null)
				return false;
		} else if (!idPerfil.equals(other.idPerfil))
			return false;
		return true;
	}
}
