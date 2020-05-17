package com.neves.starwars.api.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neves.external.api.StarWars;
import com.neves.external.api.StarWarsApiExternal;
import com.neves.external.models.PlanetaAPIExterna;
import com.neves.external.models.SWModelList;
import com.neves.starwars.api.models.Planeta;
import com.neves.starwars.api.responses.Response;
import com.neves.starwars.api.services.PlanetaService;

import retrofit2.Call;

// Mapping do MongoDB "path" é o caminho inicial da API.
@RestController
@RequestMapping(path = "/api/planetas")
public class PlanetaController {
	// Instância responsável por realizar as consultas na API do StarWars (Externa).
	private static StarWars api;
	
	// Variável estática que vai guardar a quantidade de aparições em filmes.
	private static long		qtdAparicoes = 0;
	
	
	// Instância do Serviço "Planeta".
	@Autowired
	private PlanetaService planetaService;
	
	
	// GET = Listará todos os planetas.
	@GetMapping
	public ResponseEntity<Response<List<Planeta>>> listarTodos() {
		return ResponseEntity.ok(new Response<List<Planeta>> (this.planetaService.listarTodos()));
	}
	
	// GET = Listará o planeta por ID.
	@GetMapping(path = "/listarPorId/{id}")
	public ResponseEntity<Response<Planeta>> listarPorId(@PathVariable(name = "id") String id) {
		return ResponseEntity.ok(new Response<Planeta> (this.planetaService.listarPorId(id)));
	}
	
	// GET = Listará o planeta de acordo com o nome (funciona equivalente ao like).
	@GetMapping(path = "/listarPorNome/{nome}")
	public ResponseEntity<Response<Planeta>> listarPorNome(@PathVariable(name = "nome") String nome) {
		return ResponseEntity.ok(new Response<Planeta> (this.planetaService.listarPorNome(nome)));
	}
	
	// POST = Irá inserir os parâmetros encaminhados via post e também irá realizar a consulta na API Externa a fim de recuperar a informação em quantos filmes o planeta apareceu.
	@PostMapping
	public ResponseEntity<Response<Planeta>> cadastrar(@Validated @RequestBody Planeta planeta, BindingResult result) throws Exception {
		if (result.hasErrors()) {
			List<String> erros = new ArrayList<String>();
			result.getAllErrors().forEach(erro -> erros.add(erro.getDefaultMessage()));
			return ResponseEntity.badRequest().body(new Response<Planeta>(erros));
					
		}
		
		api = StarWarsApiExternal.getApi();
		retornaTodosPlanetas(1, planeta.getNome());
		
		planeta.setQtdAparicoes(qtdAparicoes);

		return ResponseEntity.ok(new Response<Planeta>(this.planetaService.cadastrar(planeta)));	
	}
	
	// DELETE = Deleção por ID. (1=1)
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Response<Integer>> remover(@PathVariable(name = "id") String id) {
		this.planetaService.remover(id);
		return ResponseEntity.ok( new Response<Integer>(1));
	}
	
	// Função responsável pela consulta de todos os planetas enquanto houver uma próxima página. Em caso dê "match" com o nome informado o mesmo é "setado" p/ qtdAparicoes.
	public static void retornaTodosPlanetas(int page, String nome) throws Exception {
        Call<SWModelList<PlanetaAPIExterna>> planets = api.getAllPlanets(page);

        retrofit2.Response<SWModelList<PlanetaAPIExterna>> response = planets.execute();
        if (response.isSuccessful()) {
            SWModelList<PlanetaAPIExterna> data = response.body();
            int count = data.count;
            assertThat(count).isNotZero().isGreaterThan(0);
            for (PlanetaAPIExterna planet : data.results) {
            	if (planet.nome.equals(nome)) {
            		qtdAparicoes = planet.aparicoesNosFilmes.stream().count();
            	}
            }
            
            if (data.possuiProximaPagina()) {
            	String[] partes = data.next.split("\\?");
        		String[] queries = partes[1].split("=");
        		Integer pagina = queries != null && queries.length > 1 ? Integer.valueOf(queries[1]) : null;
        		retornaTodosPlanetas(pagina, nome);
            }         
        } else {
            System.out.println("Erro amigão");
        }
    }
}







