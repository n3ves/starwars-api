package com.neves.starwars.api.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neves.starwars.api.models.Planeta;
import com.neves.starwars.api.repositories.PlanetaRepository;
import com.neves.starwars.api.services.PlanetaService;

@Service
public class PlanetaServiceImpl implements PlanetaService {
	
	// Instância do repositório de Planeta.
	@Autowired
	private PlanetaRepository planetaRepository;
	
	// Lista de funções relacionadas à classe Planeta.
	// Responsável por listar todos os planetas, sem nenhum parâmetro de consulta.
	@Override
	public List<Planeta> listarTodos() {
		return this.planetaRepository.findAll();
	}
	
	// Responsável por encontrar um planeta cujo ID é idêntico ao informado.
	@Override
	public Planeta listarPorId(String id) {
		return this.planetaRepository.findOne(id);
	}
	
	// Responsável por listar todos os planetas de acordo com o parâmetro "nome".
	@Override
	public Planeta listarPorNome(String nome) {
		return this.planetaRepository.findByNomeContaining(nome);
	}
	
	// Responsável pelo cadastro de um novo Planeta.
	@Override
	public Planeta cadastrar(Planeta planeta) {
		return this.planetaRepository.save(planeta);
	}
	
	// Utilizado para deleção de um Planeta cujo ID é identico ao informado.
	@Override
	public void remover(String id) {
		this.planetaRepository.delete(id);
	}

}
