package com.neves.starwars.api.services;

import java.util.List;

import com.neves.starwars.api.models.Planeta;

public interface PlanetaService {
	
	// Lista de funções relacionadas à classe Planeta.
	
	// Responsável por listar todos os planetas, sem nenhum parâmetro de consulta.
	List<Planeta> listarTodos();
	
	// Responsável por encontrar um planeta cujo ID é idêntico ao informado.
	Planeta listarPorId(String id);
	
	// Responsável por listar todos os planetas de acordo com o parâmetro "nome".
	Planeta listarPorNome(String nome);
	
	// Responsável pelo cadastro de um novo Planeta.
	Planeta cadastrar(Planeta planeta);
	
	// Utilizado para deleção de um Planeta cujo ID é identico ao informado.
	void remover(String id);
	
}
