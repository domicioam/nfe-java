package com.dgsystems.nfeservice;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.dgsystems.nfeservice.cadastro.controllers.CertificadoController;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class CertificadoControllerTest {
	
	ObjectMapper mapper;
	MockMvc mvc;
	
	@InjectMocks
	CertificadoController certificadoController;

	@Test
	void shouldSaveUploadedCertificado() throws Exception {
		File file = new File("src/test/resources/MyDevCert.pfx");
		InputStream inputStream = new FileInputStream(file);
	    MockMultipartFile certificado = new MockMultipartFile("certificado", "MyDevCert.pfx", "application/x-pkcs12", inputStream);
		
		mvc.perform(multipart("/empresa/1/certificado/").file(certificado))
			.andExpect(status().isCreated())
			.andReturn();
	}
	
	@BeforeAll
	void setUp() {
		mapper = new ObjectMapper();
		mvc = MockMvcBuilders.standaloneSetup(certificadoController).build();
	}

}
