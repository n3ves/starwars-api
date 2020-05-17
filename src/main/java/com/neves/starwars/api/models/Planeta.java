package com.neves.starwars.api.models;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Planeta {
	
	// Declarações de váriáveis privadas.
	
	@Id
	private String id;
	private String nome;
	private String clima;
	private String terreno;
	private long   qtdAparicoes;
	
	public Planeta() {
		
	}
	
	// Getters e setters.
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@NotEmpty(message = "Ei! Preencha o nome do seu planeta. =)")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@NotEmpty(message = "Opa, não identificamos nenhum clima no seu cadastro, pode revisar novamente? Hehe!")
	public String getClima() {
		return clima;
	}

	public void setClima(String clima) {
		this.clima = clima;
	}

	@NotEmpty(message = "Depois de tudo isso juntos, você simplesmente ignora o terreno? ¯\\_(ツ)_/¯")
	public String getTerreno() {
		return terreno;
	}

	public void setTerreno(String terreno) {
		this.terreno = terreno;
	}

	// Variável preenchida por consulta externa (não há necessidade de estar no json enviado via post).
	public long getQtdAparicoes() {
		return qtdAparicoes;
	}

	public void setQtdAparicoes(long qtdAparicoes) {
		this.qtdAparicoes = qtdAparicoes;
	}
	
}
