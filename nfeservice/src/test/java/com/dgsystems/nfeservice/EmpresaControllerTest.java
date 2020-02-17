package com.dgsystems.nfeservice;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

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

import com.dgsystems.nfeservice.cadastro.configuracao.Configuracao;
import com.dgsystems.nfeservice.cadastro.controllers.EmpresaController;
import com.dgsystems.nfeservice.cadastro.empresa.Empresa;
import com.dgsystems.nfeservice.cadastro.empresa.Endereco;
import com.dgsystems.nfeservice.cadastro.empresa.RegimeTributario;
import com.dgsystems.nfeservice.cadastro.repositories.ConfiguracaoRepository;
import com.dgsystems.nfeservice.cadastro.repositories.EmpresaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class EmpresaControllerTest {
	
	ObjectMapper mapper;
	MockMvc mvc;
	
	@Mock
	EmpresaRepository empresaRepository;

	@InjectMocks
	EmpresaController empresaController;

	@Test
	void should_return_status_created_when_empresa_is_valid() throws JsonProcessingException, Exception {
		Endereco endereco = new Endereco("70000", "Quadra 100 conjunto 10", "Samambaia", "GO", "Palmas", "10");
		Empresa empresa = new Empresa("RAZAO SOCIAL", "05469874563215", "NOME FANTASIA", "1234569876542", "1234569876542", RegimeTributario.RegimeNormal, "5469873", endereco);
		
		mvc.perform(post("/empresa").contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(empresa)))
				.andExpect(status().isCreated());
	}
	
	@Test
	void should_return_bad_request_when_empresa_is_invalid() throws JsonProcessingException, Exception {
		Endereco endereco = new Endereco("70000", "Quadra 100 conjunto 10", "Samambaia", "GO", "Palmas", "10");
		Empresa empresa = new Empresa("RAZAO SOCIAL", "05469874563215", "NOME FANTASIA", "", "1234569876542", RegimeTributario.RegimeNormal, "5469873", endereco);
		
		mvc.perform(post("/empresa").contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(empresa)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void should_return_bad_request_when_endereco_is_invalid() throws JsonProcessingException, Exception {
		Endereco endereco = new Endereco("", "Quadra 100 conjunto 10", "Samambaia", "GO", "Palmas", "10");
		Empresa empresa = new Empresa("RAZAO SOCIAL", "05469874563215", "NOME FANTASIA", "1234569876542", "1234569876542", RegimeTributario.RegimeNormal, "5469873", endereco);
		
		mvc.perform(post("/empresa").contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(empresa)))
				.andExpect(status().isBadRequest());
	}
	
	@BeforeAll
	void setUp() {
		mapper = new ObjectMapper();
		mvc = MockMvcBuilders.standaloneSetup(empresaController).build();
	}
}
