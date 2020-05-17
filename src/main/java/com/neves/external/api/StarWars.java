package com.neves.external.api;

import com.neves.external.models.PlanetaAPIExterna;
import com.neves.external.models.SWModelList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StarWars {
	
	// Responsável pelo retorno de todos os planetas (parâmetro 'page' é utilizado p/ informar qual página de consulta).
    @GET("planets/")
    Call<SWModelList<PlanetaAPIExterna>> getAllPlanets(@Query("page") Integer page);
    
}
