package com.dgsystems.nfeservice.integrationtests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.StreamSupport;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.dgsystems.nfeservice.cadastro.controllers.ProdutoController;
import com.dgsystems.nfeservice.cadastro.destinatario.Destinatario;
import com.dgsystems.nfeservice.cadastro.empresa.Empresa;
import com.dgsystems.nfeservice.cadastro.empresa.Endereco;
import com.dgsystems.nfeservice.cadastro.empresa.RegimeTributario;
import com.dgsystems.nfeservice.cadastro.produto.Icms;
import com.dgsystems.nfeservice.cadastro.produto.Imposto;
import com.dgsystems.nfeservice.cadastro.produto.Pis;
import com.dgsystems.nfeservice.cadastro.produto.Produto;
import com.dgsystems.nfeservice.cadastro.produto.Regime;
import com.dgsystems.nfeservice.cadastro.repositories.EmpresaRepository;
import com.dgsystems.nfeservice.cadastro.repositories.ProdutoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureTestDatabase
@Transactional
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class ProdutoControllerIntegrationTests {
	ObjectMapper mapper;
	MockMvc mvc;
	
	@Resource
	private EmpresaRepository empresaRepository;
	@Resource
	private ProdutoRepository produtoRepository;
	@Resource
	private ProdutoController produtoController;
	
	private Empresa empresa;
	
	@Test
	void should_add_produto_to_list_of_produtos_of_the_empresa() throws JsonProcessingException, Exception {
		List<Imposto> impostos = new ArrayList<>();
		impostos.add(new Icms(0, "60", 0));
		impostos.add(new Pis(0, "04", Regime.Cumulativo));
		
		Produto produto = new Produto("Botijao de gas P13", "27111910", "UN", 61.5, impostos);
		produto.setEmpresa(empresa);
		
		MvcResult result = mvc.perform(post("/empresa/" + empresa.getId() + "/produto").contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(produto)))
				.andExpect(status().isCreated())
				.andReturn();
		
		EntityModel<Produto> entity = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<EntityModel<Produto>>() {});
		Iterable<Produto> produtosEmpresa = produtoRepository.findAllByEmpresaId(empresa.getId());
		
		boolean hasMatch = StreamSupport.stream(produtosEmpresa.spliterator(), false)
						.anyMatch(entity.getContent()::equals);
		
		assertTrue(hasMatch);
	}
	
	@BeforeAll
	void setUp() {
		Endereco endereco = new Endereco("70000", "Quadra 100 conjunto 10", "Samambaia", "GO", "Palmas", "10");
		empresa = new Empresa("RAZAO SOCIAL", "05469874563215", "NOME FANTASIA", "1234569876542", "1234569876542", RegimeTributario.RegimeNormal, "5469873", endereco);
		empresa = empresaRepository.save(empresa);
		
		mapper = new ObjectMapper();
		mvc = MockMvcBuilders.standaloneSetup(produtoController).build();
	}
	
	@Configuration
	@TestPropertySource("test.properties")
	@EnableTransactionManagement
	class InMemoryTestConfig {
		@Autowired
		private Environment env;
		
	    @Bean
	    public DataSource dataSource() {
	        DriverManagerDataSource dataSource = new DriverManagerDataSource();
	        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
	        dataSource.setUrl(env.getProperty("jdbc.url"));
	        dataSource.setUsername(env.getProperty("jdbc.user"));
	        dataSource.setPassword(env.getProperty("jdbc.pass"));
	 
	        return dataSource;
	    }
	}
}
