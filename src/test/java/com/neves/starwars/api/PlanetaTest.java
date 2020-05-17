package com.neves.starwars.api;

import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neves.starwars.api.controllers.PlanetaController;

@SpringBootTest()
@ActiveProfiles("teste")

public class PlanetaTest {

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before()
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void testeRetornoPlanetaPorID() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		MockHttpServletRequestBuilder requisicao = MockMvcRequestBuilders.get("/planetas/" + 1L);
		ResultActions results = mockMvc.perform(requisicao);
		results.andExpect(MockMvcResultMatchers.status().isOk());
		String jsonResponse = results.andReturn().getResponse().getContentAsString();
		System.out.println(jsonResponse);

		PlanetaController resposta = objectMapper.readValue(jsonResponse, PlanetaController.class);

		assertNotNull(resposta);
	}

	@Test
	public void testeRetornoPlanetaPorNOME() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		ResultActions results = mockMvc.perform(MockMvcRequestBuilders.get("/planetas/listarPorNome/" + "Jakku"));
		results.andExpect(MockMvcResultMatchers.status().isOk());

		String jsonResponse = results.andReturn().getResponse().getContentAsString();
		System.out.println(jsonResponse);

		PlanetaController resposta = objectMapper.readValue(jsonResponse, PlanetaController.class);

		assertNotNull(resposta);
	}

	@Test
	public void testeDelecaoPlaneta() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/planetas/" + 1L)).andExpect(MockMvcResultMatchers.status().isOk());
		mockMvc.perform(MockMvcRequestBuilders.delete("/planetas/" + 1L))
				.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Test
	public void deveListarTodosPlanetasBancoDeDados() throws Exception {
		MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.get("/planetas"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		String textoResposta = resultado.getResponse().getContentAsString();

		ObjectMapper mapper = new ObjectMapper();

		PlanetaController resposta = mapper.readValue(textoResposta, PlanetaController.class);

		assertNotNull(resposta);
	}
}