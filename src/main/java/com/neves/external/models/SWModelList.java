package com.neves.external.models;

import java.io.Serializable;
import java.util.List;

public class SWModelList<T> implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Retorno "padrão" da consulta na API do StarWars.
	public int count;
    public String next;
    public String previous;
    public List<T> results;
    
    // Verificação se vai possuir uma próxima página para consulta.
    public boolean possuiProximaPagina() {
        return (next != null && !next.isEmpty());
    }
}