package com.optimusDev.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="livro")
public class Livro implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long idLivro;
	private String titulo;
	private String edicao;
	private Arquivo capa;
	private Arquivo pdf;
	private String observacao;
	private Editora editora;
	private Genero genero;
	private Conta conta;
	private List<Autor> autores;
	private List<Categoria> categoria;
	private Integer visualizacoes;
	
	private String capaBase64;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getIdLivro() {
		return idLivro;
	}
	public void setIdLivro(Long idLivro) {
		this.idLivro = idLivro;
	}
	
	@NotNull
	@Column(name="titulo")
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	@NotNull
	@Column(name="edicao")
	public String getEdicao() {
		return edicao;
	}
	public void setEdicao(String edicao) {
		this.edicao = edicao;
	}	
	
	@OneToOne
	@JoinColumn(name = "id_editora")
	public Editora getEditora() {
		return editora;
	}
	public void setEditora(Editora editora) {
		this.editora = editora;
	}
	
	@OneToOne
	@JoinColumn(name="id_genero")
	public Genero getGenero() {
		return genero;
	}
	public void setGenero(Genero genero) {
		this.genero = genero;
	}
	
	@OneToOne
	@JoinColumn(name="conta")
	public Conta getConta() {
		return conta;
	}
	public void setConta(Conta conta) {
		this.conta = conta;
	}
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="livro_categoria", joinColumns = @JoinColumn(name="id_livro"), inverseJoinColumns=@JoinColumn(name="id_categoria"))
	public List<Categoria> getCategoria() {
		return categoria;
	}
	public void setCategoria(List<Categoria> categoria) {
		this.categoria = categoria;
	}
	
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name="livro_autor", joinColumns = @JoinColumn(name="id_livro"), inverseJoinColumns=@JoinColumn(name="id_autor"))
	public List<Autor> getAutores() {		
		return autores;
	}
	public void setAutores(List<Autor> autores) {
		this.autores = autores;
	}
	
	@NotNull
	@OneToOne
	@JoinColumn(name="capa")
	public Arquivo getCapa() {
		return capa;
	}
	public void setCapa(Arquivo capa) {
		this.capa = capa;
	}	
	
	@Transient
	public String getCapaBase64() {
		return capaBase64;
	}
	public void setCapaBase64(String capaBase64) {
		this.capaBase64 = capaBase64;
	}
	
	@NotNull
	@OneToOne
	@JoinColumn(name="pdf")
	public Arquivo getPdf() {
		return pdf;
	}
	public void setPdf(Arquivo pdf) {
		this.pdf = pdf;
	}
	
	@Column(name="observacao")
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	@Column(name="visualizacoes")
	public Integer getVisualizacoes() {
		return visualizacoes;
	}
	
	public void setVisualizacoes(Integer visualizacoes) {
		this.visualizacoes = visualizacoes;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idLivro == null) ? 0 : idLivro.hashCode());
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
		Livro other = (Livro) obj;
		if (idLivro == null) {
			if (other.idLivro != null)
				return false;
		} else if (!idLivro.equals(other.idLivro))
			return false;
		return true;
	}
	
	@Override
	public String toString(){
		return this.idLivro.toString();
	}	
}
