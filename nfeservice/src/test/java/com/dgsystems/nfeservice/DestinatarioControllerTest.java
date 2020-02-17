package com.dgsystems.nfeservice;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.dgsystems.nfeservice.cadastro.controllers.DestinatarioController;
import com.dgsystems.nfeservice.cadastro.destinatario.Destinatario;
import com.dgsystems.nfeservice.cadastro.destinatario.Endereco;
import com.dgsystems.nfeservice.cadastro.repositories.DestinatarioRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class DestinatarioControllerTest {
	@Mock
	DestinatarioRepository destinatarioRepository;
	@InjectMocks
	DestinatarioController destinatarioController;
	
	ObjectMapper mapper;
	MockMvc mvc;

	@Test
	void should_return_status_created_when_destinatario_is_valid() throws JsonProcessingException, Exception {
		Endereco endereco = new Endereco("Quadra 310 conjunto 15", "20", "Ceilândia", "DF", "Brasília");
		Destinatario destinatario = new Destinatario("Domício", "+351915960883", "domicioam@gmail.com", endereco);
		
		mvc.perform(post("/empresa/0/destinatario").contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(destinatario)))
				.andExpect(status().isCreated());
	}
	
	@Test
	void should_return_bad_request_when_destinatario_is_invalid() throws JsonProcessingException, Exception {
		Endereco endereco = new Endereco("Quadra 310 conjunto 15", "20", "Ceilândia", "DF", "Brasília");
		Destinatario destinatario = new Destinatario("", "+351915960883", "domicioam@gmail.com", endereco);
		
		mvc.perform(post("/empresa/0/destinatario").contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(destinatario)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void should_return_bad_request_when_endereco_is_invalid() throws JsonProcessingException, Exception {
		Endereco endereco = new Endereco("Quadra 310 conjunto 15", "", "Ceilândia", "DF", "Brasília");
		Destinatario destinatario = new Destinatario("Domício", "+351915960883", "domicioam@gmail.com", endereco);
		
		mvc.perform(post("/empresa/0/destinatario").contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(destinatario)))
				.andExpect(status().isBadRequest());
	}
	
	@BeforeAll
	void setUp() {
		mapper = new ObjectMapper();
		mvc = MockMvcBuilders.standaloneSetup(destinatarioController).build();
	}

}
