package com.neves.starwars.api.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.neves.starwars.api.models.Planeta;

public interface PlanetaRepository extends MongoRepository<Planeta, String> {
	// Implementação do "findByNomeContaining" para realizar as buscas pelo nome do Planeta.
	Planeta findByNomeContaining(final String nome);
}
