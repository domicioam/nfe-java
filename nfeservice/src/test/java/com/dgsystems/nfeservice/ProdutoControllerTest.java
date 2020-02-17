package com.dgsystems.nfeservice;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.dgsystems.nfeservice.cadastro.controllers.ProdutoController;
import com.dgsystems.nfeservice.cadastro.produto.Icms;
import com.dgsystems.nfeservice.cadastro.produto.Imposto;
import com.dgsystems.nfeservice.cadastro.produto.Pis;
import com.dgsystems.nfeservice.cadastro.produto.Produto;
import com.dgsystems.nfeservice.cadastro.produto.Regime;
import com.dgsystems.nfeservice.cadastro.repositories.ProdutoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class ProdutoControllerTest {

	@InjectMocks
	ProdutoController produtoController;
	
	@Mock
	ProdutoRepository produtoRepository;
	
	ObjectMapper mapper;
	MockMvc mvc;
	
	@Test
	void should_save_produto_and_return_exact_produto_with_new_id() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		List<Imposto> impostos = new ArrayList<>();
		impostos.add(new Icms(0, "60", 0));
		impostos.add(new Pis(0, "04", Regime.Cumulativo));
		
		Produto mockedEntity = new Produto("Botijão de gás P13", "27111910", "UN", 61.5, impostos);
		mockedEntity.setId(1);
		when(produtoRepository.save(any(Produto.class))).thenReturn(mockedEntity);
		
		Produto produto = new Produto("Botijão de gás P13", "27111910", "UN", 61.5, impostos);
		EntityModel<Produto> responseEntity = produtoController.save(produto, 1);
		
		assertEquals(responseEntity.getContent(), mockedEntity);
	}
	
	@Test
	void should_return_bad_request_when_trying_to_save_invalid_produto() throws JsonProcessingException, Exception {
		List<Imposto> impostos = new ArrayList<>();
		impostos.add(new Icms(0, "60", 0));
		impostos.add(new Pis(0, "04", Regime.Cumulativo));
		
		Produto produto = new Produto("", "27111910", "UN", 61.5, impostos);
		
		MvcResult result = mvc.perform(post("/empresa/0/produto").contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(produto)))
				.andExpect(status().isBadRequest())
				.andReturn();
		
		assertNotNull(result.getResolvedException());
		assertNotNull(result.getResponse().getContentAsString());
	}
	
	@Test
	void should_return_bad_request_when_trying_to_save_produto_without_impostos() throws JsonProcessingException, Exception {
		Produto produto = new Produto("Botijão de gás", "27111910", "UN", 61.5, new ArrayList<Imposto>());
		
		MvcResult result = mvc.perform(post("/empresa/0/produto").contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(produto)))
				.andExpect(status().isBadRequest())
				.andReturn();
		
		assertNotNull(result.getResolvedException());
		assertNotNull(result.getResponse().getContentAsString());
	}
	
	@Test
	void should_return_bad_request_when_trying_to_save_produto_with_invalid_imposto() throws JsonProcessingException, Exception {
		List<Imposto> impostos = new ArrayList<>();
		impostos.add(new Icms(0, "", 0));
		impostos.add(new Pis(0, "04", Regime.Cumulativo));
		
		Produto produto = new Produto("Botijão de gás", "27111910", "UN", 61.5, impostos);
		
		MvcResult result = mvc.perform(post("/empresa/0/produto").contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(produto)))
				.andExpect(status().isBadRequest())
				.andReturn();
		
		assertNotNull(result.getResolvedException());
		assertNotNull(result.getResponse().getContentAsString());
	}
	
	@BeforeAll
	void setUp() {
		mapper = new ObjectMapper();
		mvc = MockMvcBuilders.standaloneSetup(produtoController).build();
	}
}
