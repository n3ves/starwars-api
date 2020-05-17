package com.neves.external.models;

import java.io.Serializable;
import java.util.List;

public class PlanetaAPIExterna implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// Campos que preciso p/ realizar a verificação e preencher o campo "qtdAparicoes".
	public String 		nome;
    public List<String> aparicoesNosFilmes;
    
}