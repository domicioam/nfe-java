package com.dgsystems.nfeservice.integrationtests;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.jupiter.api.AfterAll;
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

import com.dgsystems.nfeservice.cadastro.controllers.DestinatarioController;
import com.dgsystems.nfeservice.cadastro.destinatario.Destinatario;
import com.dgsystems.nfeservice.cadastro.empresa.Empresa;
import com.dgsystems.nfeservice.cadastro.empresa.Endereco;
import com.dgsystems.nfeservice.cadastro.empresa.RegimeTributario;
import com.dgsystems.nfeservice.cadastro.repositories.DestinatarioRepository;
import com.dgsystems.nfeservice.cadastro.repositories.EmpresaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@AutoConfigureTestDatabase
@Transactional
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class DestinatarioControllerIntegrationTests {

	@Resource
	private DestinatarioController destinatarioController;
	@Resource
	private EmpresaRepository empresaRepository;
	@Resource
	private DestinatarioRepository destinatarioRepository;
	
	private Empresa empresa;
	private Empresa otherEmpresa;
	
	ObjectMapper mapper;
	MockMvc mvc;
	
	@Test
	void should_add_destinatario_to_list_of_destinatarios_of_the_empresa() throws JsonProcessingException, Exception {
		com.dgsystems.nfeservice.cadastro.destinatario.Endereco endereco = new com.dgsystems.nfeservice.cadastro.destinatario.Endereco("Quadra 310 conjunto 15", "20", "Ceilândia", "DF", "Brasília");
		Destinatario destinatario = new Destinatario("Domício", "+351915960883", "domicioam@gmail.com", endereco);
		
		MvcResult result = mvc.perform(post("/empresa/" + empresa.getId() + "/destinatario").contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(destinatario)))
				.andExpect(status().isCreated())
				.andReturn();
		
		Destinatario destinatarioResult = mapper.readValue(result.getResponse().getContentAsString(), Destinatario.class);
		List<Empresa> empresas = new ArrayList<Empresa>();
		empresaRepository.findAll().forEach(empresas::add);
		Collection<EntityModel<Destinatario>> destinatarios = destinatarioController.findAllByEmpresaId(empresa.getId()).getBody().getContent();
		
		assertNotNull(destinatarioResult.getEmpresa());
	}
	
	@BeforeAll
	void setUp() {
		Endereco endereco = new Endereco("70000", "Quadra 100 conjunto 10", "Samambaia", "GO", "Palmas", "10");
		empresa = new Empresa("RAZAO SOCIAL", "05469874563215", "NOME FANTASIA", "1234569876542", "1234569876542", RegimeTributario.RegimeNormal, "5469873", endereco);
		empresa = empresaRepository.save(empresa);
		
		// to validate if it is returning only entities related to the correct empresa 
		Endereco otherEndereco = new Endereco("70000", "Quadra 100 conjunto 10", "Samambaia", "GO", "Palmas", "10");
		otherEmpresa = new Empresa("RAZAO SOCIAL", "05469874563215", "NOME FANTASIA", "1234569876542", "1234569876542", RegimeTributario.RegimeNormal, "5469873", otherEndereco);
		empresaRepository.save(otherEmpresa);
		
		com.dgsystems.nfeservice.cadastro.destinatario.Endereco destinatarioEndereco = new com.dgsystems.nfeservice.cadastro.destinatario.Endereco("Quadra 310 conjunto 15", "20", "Ceilândia", "DF", "Brasília");
		Destinatario destinatario = new Destinatario("Domício", "+351915960883", "domicioam@gmail.com", destinatarioEndereco);
		destinatario.setEmpresa(otherEmpresa);
		
		destinatarioRepository.save(destinatario);
		
		mapper = new ObjectMapper();
		mvc = MockMvcBuilders.standaloneSetup(destinatarioController).build();
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
