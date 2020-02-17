package com.dgsystems.nfeservice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
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
import com.dgsystems.nfeservice.cadastro.controllers.ConfiguracaoController;
import com.dgsystems.nfeservice.cadastro.empresa.Empresa;
import com.dgsystems.nfeservice.cadastro.empresa.Endereco;
import com.dgsystems.nfeservice.cadastro.empresa.RegimeTributario;
import com.dgsystems.nfeservice.cadastro.repositories.ConfiguracaoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class ConfiguracaoControllerTest {
	@Mock
	ConfiguracaoRepository configuracaoRepository;
	
	@InjectMocks
	ConfiguracaoController configuracaoController;
	
	ObjectMapper mapper;
	MockMvc mvc;

	@Test
	void should_save_configuracao_when_configuracao_is_valid() throws JsonProcessingException, Exception {
		Configuracao configuracao = new Configuracao("001", 1, "001", 1, "123456", "123456789123456789123456789123456789", null);
		
		when(configuracaoRepository.save(any(Configuracao.class))).thenReturn(configuracao);
		
		mvc.perform(post("/empresa/1/configuracao/").contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(configuracao)))
				.andExpect(status().isCreated());
	}
	
	@Test
	void should_find_configuracao_when_configuracao_exists() throws JsonProcessingException, Exception {
		Configuracao configuracaoMock = new Configuracao("001", 1, "001", 1, "123456", "123456789123456789123456789123456789", null);
		configuracaoMock.setId(1);
		Optional<Configuracao> configuracao = Optional.of(configuracaoMock);
		
		when(configuracaoRepository.findByIdAndEmpresaId(1, 1)).thenReturn(configuracao);
		
		mvc.perform(get("/empresa/1/configuracao/1").contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	void should_find_all_configuracao_when_empresa_exists() throws JsonProcessingException, Exception {
		Configuracao configuracaoMock = new Configuracao("001", 1, "001", 1, "123456", "123456789123456789123456789123456789", null);
		configuracaoMock.setId(1);
		List<Configuracao> configuracoes = new ArrayList<>();
		configuracoes.add(configuracaoMock);
		
		when(configuracaoRepository.findAllByEmpresaId(1)).thenReturn(configuracoes);
		
		mvc.perform(get("/empresa/1/configuracao").contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	void should_return_bad_request_when_configuracao_is_invalid() throws JsonProcessingException, Exception {
		Configuracao configuracao = new Configuracao("001", 1, "001", 1, "123456", "", null);
		
		mvc.perform(post("/empresa/1/configuracao/").contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(configuracao)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void should_delete_configuracao_when_configuracao_exists() throws JsonProcessingException, Exception {
		mvc.perform(delete("/empresa/1/configuracao/1").contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}
	
	@Test
	void should_update_configuracao_when_configuracao_is_valid() throws JsonProcessingException, Exception {
		Configuracao configuracao = new Configuracao("001", 1, "001", 1, "123456", "123456789123456789123456789123456789", null);
		
		when(configuracaoRepository.findById(any(Integer.class))).thenReturn(Optional.of(configuracao));
		when(configuracaoRepository.save(any(Configuracao.class))).thenReturn(configuracao);
		
		mvc.perform(put("/empresa/1/configuracao/1").contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(configuracao)))
				.andExpect(status().isOk());
	}
	
	@BeforeAll
	void setUp() {
		mapper = new ObjectMapper();
		mvc = MockMvcBuilders.standaloneSetup(configuracaoController).build();
	}

}
