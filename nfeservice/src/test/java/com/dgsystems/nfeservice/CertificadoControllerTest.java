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
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.dgsystems.nfeservice.cadastro.certificado.Certificado;
import com.dgsystems.nfeservice.cadastro.controllers.CertificadoController;
import com.dgsystems.nfeservice.cadastro.repositories.CertificadoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class CertificadoControllerTest {
	
	ObjectMapper mapper;
	MockMvc mvc;
	
	@InjectMocks
	CertificadoController certificadoController;
	
	@Mock
	CertificadoRepository certificadoRepository;

	@Test
	void shouldSaveUploadedCertificado() throws Exception {
		File file = new File("src/test/resources/MyDevCert.pfx");
		InputStream inputStream = new FileInputStream(file);
	    MockMultipartFile multipartFile = new MockMultipartFile("certificado", "MyDevCert.pfx", "application/x-pkcs12", inputStream);
		
		mvc.perform(multipart("/empresa/1/certificado/").file(multipartFile))
			.andExpect(status().isCreated())
			.andReturn();
		
		verify(certificadoRepository, times(1)).save(Mockito.any(Certificado.class));
	}
	
	@Test
	void should404WhenMissingCertificadoToUpload() throws Exception {
		File file = new File("src/test/resources/MyDevCert.pfx");
		InputStream inputStream = new FileInputStream(file);
	    MockMultipartFile certificado = new MockMultipartFile("not_expected_name", "MyDevCert.pfx", "application/x-pkcs12", inputStream);
		
		mvc.perform(multipart("/empresa/1/certificado/").file(certificado))
			.andExpect(status().isBadRequest())
			.andReturn();
	}
	
	@BeforeAll
	void setUp() {
		mapper = new ObjectMapper();
		mvc = MockMvcBuilders.standaloneSetup(certificadoController).build();
	}

}
